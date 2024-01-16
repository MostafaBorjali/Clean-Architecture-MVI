package com.borjali.data.repository

import android.annotation.SuppressLint
import com.borjali.data.datasource.cache.worker.WorkerDao
import com.borjali.data.datasource.network.api.CleanAppApi
import com.borjali.data.datasource.network.api.StorageApi
import com.borjali.data.datasource.network.utils.NetworkBoundResource
import com.borjali.data.mapper.toWorkerDto
import com.borjali.data.mapper.toWorkerEntity
import com.borjali.domain.Constants.Companion.BIOZOON
import com.borjali.domain.Constants.Companion.FILE
import com.borjali.domain.Constants.Companion.IMAGE_JPG
import com.borjali.domain.Constants.Companion.ISO_8601
import com.borjali.domain.Constants.Companion.TEXT_PLAIN
import com.borjali.domain.model.base.ServerResponse
import com.borjali.domain.model.upload.UploadResponseDto
import com.borjali.domain.model.worker.AttachmentDto
import com.borjali.domain.model.worker.AvatarDto
import com.borjali.domain.model.worker.WorkerDto
import com.borjali.domain.repository.WorkerRepository
import com.borjali.domain.utils.DataState
import com.borjali.domain.utils.DataState.SUCCESS
import com.borjali.domain.viewstate.StateOfView
import com.borjali.domain.viewstate.WorkerViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.ZoneId
import javax.inject.Inject

/**
 * Implementation class of [WorkerRepository]
 */
class WorkerRepositoryImpl @Inject constructor(
    private val cleanAppApi: CleanAppApi,
    private val storageApi: StorageApi,
    private val workerDao: WorkerDao,
) : WorkerRepository {

    /**
     * this function will get all worker of order subset .
     * @param stateOfView
     * the state of view is to Determine who called this method.
     * @return a flow of  state of Success operation  wrapped with DataState and list of workers.
     */
    override fun getWorkerOfProducts(stateOfView: StateOfView) = flow {
        emitAll(
            object : NetworkBoundResource<List<WorkerDto>, Unit, WorkerViewState>(
                apiCall = {
                    cleanAppApi.getWorkersOfProducts()
                }
            ) {
                override suspend fun handleNetworkSuccess(
                    response: ServerResponse<List<WorkerDto>?>?
                ): DataState<WorkerViewState> {

                    return SUCCESS(
                        data = WorkerViewState(workers = response?.data!!),
                        stateOfView = stateOfView,
                    )
                }
            }.result
        )
    }


    /**
     * this function will upload image of worker.
     * first we get image file of worker and upload on storage server and then we get success
     * response, we call another api for update worker image url
     * @param workerDto
     * @param file
     * @param stateOfView
     * the state of view is to Determine who called this method.
     * the workerDto contain workerId to find user
     * the file is image file worker
     * @return a flow of  state of Success operation  wrapped with DataState.
     */

    override fun uploadWorkerAvatar(workerDto: WorkerDto, file: File, stateOfView: StateOfView) =
        flow {
            val perf = "$BIOZOON${workerDto.id}"
                .toRequestBody(TEXT_PLAIN.toMediaTypeOrNull())
            val fileBody = MultipartBody.Part.createFormData(
                name = FILE,
                filename = file.absolutePath,
                body = file.asRequestBody(IMAGE_JPG.toMediaTypeOrNull())
            )
            object :
                NetworkBoundResource<UploadResponseDto, Unit, UploadResponseDto>(
                    apiCall = {
                        storageApi.upload(fileBody, perf)
                    }

                ) {
                override suspend fun handleNetworkSuccess(
                    response: ServerResponse<UploadResponseDto?>?
                ): DataState<UploadResponseDto> {

                    return SUCCESS(
                        data = response?.data
                    )
                }
            }.result.flowOn(Dispatchers.IO)
                .collect { dataState ->
                    when (dataState) {
                        is SUCCESS -> {
                            emitAll(
                                object : NetworkBoundResource<Unit, WorkerDto, WorkerViewState>(
                                    apiCall = {
                                        cleanAppApi.updateWorkerAvatar(
                                            workerDto.id, AvatarDto(
                                                AttachmentDto(
                                                    cover = dataState.data?.thumbnailUrl!!,
                                                    url = dataState.data?.fileUrl!!
                                                )
                                            )
                                        )
                                    }
                                ) {
                                    override suspend fun handleNetworkSuccess(
                                        response: ServerResponse<Unit?>?
                                    ): DataState<WorkerViewState> {
                                        workerDao.update(workerDto.apply {
                                            attachment =
                                                AttachmentDto(
                                                    cover = dataState.data?.thumbnailUrl!!,
                                                    url = dataState.data?.fileUrl!!
                                                )
                                        }.toWorkerEntity())
                                        return SUCCESS(
                                            WorkerViewState(
                                                workers = workerDao.getUsers()
                                                    .map { it.toWorkerDto() }
                                            ),
                                            stateOfView = stateOfView
                                        )
                                    }
                                }.result
                            )

                        }

                        is DataState.ERROR -> {
                            emit(DataState.ERROR(WorkerViewState(message = "Cannot Upload Picture!")))
                            Timber.d("upload error is ${dataState.stateMessage?.message}")

                        }

                        else -> {}
                    }
                }
        }

    /**
     * this function get time difference in minute.
     * if difference time more than 480  minute we will delete work log in work log table in data base.
     * @param startedTimeIso
     * @param currentTimeIso
     * the startedTimeIso is time of started work log.
     * the currentTimeIso is current time of os
     * @return a long number that minute.
     */
    @SuppressLint("SimpleDateFormat")
    private fun getTimeDifferenceInMinutes(startedTimeIso: String, currentTimeIso: String): Long {
        val format = SimpleDateFormat(ISO_8601)
        val startDateTime =
            format.parse(startedTimeIso)?.toInstant()?.atZone(ZoneId.systemDefault())
        val endDateTime = format.parse(currentTimeIso)?.toInstant()?.atZone(ZoneId.systemDefault())
        val duration = Duration.between(startDateTime, endDateTime)
        return duration.toMinutes().also {
            Timber.d("Duration is = $it")
        }
    }

}
