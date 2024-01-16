package com.borjali.presentation.ui.worker.workerlist

import android.graphics.BitmapFactory
import android.os.Environment
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.widget.doOnTextChanged
import dagger.hilt.android.AndroidEntryPoint
import com.borjali.domain.model.event.EventErrorInternetConnection
import com.borjali.domain.model.event.EventListIsEmptyResult
import com.borjali.domain.model.worker.WorkerDto
import com.borjali.domain.utils.DataState
import com.borjali.domain.utils.EventOfCleanApp
import com.borjali.domain.utils.MessageType
import com.borjali.domain.viewstate.StateOfView
import com.borjali.domain.viewstate.StateOfView.GetWorker
import com.borjali.presentation.R
import com.borjali.presentation.databinding.FragmentWorkersBinding
import com.borjali.presentation.extensions.bitmapToFile
import com.borjali.presentation.extensions.isOnline
import com.borjali.presentation.extensions.navigate
import com.borjali.presentation.extensions.requireCameraPermissions
import com.borjali.presentation.extensions.requireWritePermissions
import com.borjali.presentation.extensions.scaleBitmapAndKeepRation
import com.borjali.presentation.extensions.snackMessage
import com.borjali.presentation.ui.base.BaseFragment
import com.borjali.presentation.ui.worker.WorkerEventState.GetWorkers
import com.borjali.presentation.ui.worker.WorkerEventState.UploadWorkerAvatar
import com.borjali.presentation.ui.worker.WorkerViewModel
import timber.log.Timber
import java.io.File

@AndroidEntryPoint
class WorkerFragment :
    BaseFragment<FragmentWorkersBinding, WorkerViewModel>(FragmentWorkersBinding::inflate) {
    private var tempFilePath: String? = null
    private lateinit var adapter: WorkerListAdapter

    private lateinit var workerDto: WorkerDto
    private val takeCameraPicture = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess)
            tempFilePath?.let { path ->
                val file = File(path)
                BitmapFactory.decodeFile(file.path).scaleBitmapAndKeepRation(
                    800, 800
                )?.let { file.bitmapToFile(it) }?.let {
                    if (requireContext().isOnline)
                        viewModel.setEventState(
                            UploadWorkerAvatar(StateOfView.UploadWorkerAvatar, workerDto, it)
                        )
                    else
                        EventOfCleanApp.send(EventErrorInternetConnection)
                }
            }
    }

    /**
     * Start listening to Data State Event
     */
    override fun subscribeObservers() {
        initView()
        viewModel.dataState.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.SUCCESS -> {

                    when (dataState.stateOfView) {
                        is GetWorker -> {
                            dataState.data?.workers?.let {
                                fillUserRecyclerView(it)
                            }
                        }

                        is StateOfView.UploadWorkerAvatar -> {
                            if (requireContext().isOnline)
                                viewModel.setEventState(GetWorkers(GetWorker))
                            else
                                EventOfCleanApp.send(EventErrorInternetConnection)
                        }

                        else -> {}
                    }

                }

                is DataState.ERROR -> {

                    binding.workerProgressBar.visibility = GONE
                    binding.userRecyclerView.visibility = VISIBLE
                    dataState.stateMessage?.message?.let {
                        snackMessage(it, MessageType.ERROR)
                    }

                }

                is DataState.LOADING -> {
                    if (dataState.loading) {
                        binding.userRecyclerView.visibility = INVISIBLE
                        binding.emptyResultWorker.visibility = GONE
                        binding.workerProgressBar.visibility = VISIBLE
                        binding.workerPullToRefresh.isRefreshing = false
                    }
                }

                else -> {}
            }


        }
        EventOfCleanApp.registerEvent(EventListIsEmptyResult::class.java) {
            emptyResultHandle(it.isEmpty)
        }
    }

    private fun initView() {
        binding.workerProgressBar.visibility = VISIBLE
        getWorkers()
        searchWorkerList()
        binding.workerPullToRefresh.setOnRefreshListener {
            getWorkers() }
    }

    /**
     * search worker in list
     */
    private fun searchWorkerList() {
        binding.searchInput.doOnTextChanged { text, _, _, _ ->
            if (this::adapter.isInitialized)
                adapter.filter(text.toString())

        }
    }
    /**
     * set event for get worker list
     */
    private fun getWorkers() {
        if (requireContext().isOnline){
            viewModel.setEventState(GetWorkers(GetWorker))
            binding.searchInput.text.clear()
        }
        else {
            binding.workerPullToRefresh.isRefreshing = false
            EventOfCleanApp.send(EventErrorInternetConnection)
            binding.workerProgressBar.visibility = GONE
        }
    }
    /**
     * show worker list
     * @param workers is worker list
     */
    private fun fillUserRecyclerView(workers: List<WorkerDto>) {
        binding.userRecyclerView.visibility = VISIBLE
        adapter = WorkerListAdapter(workers) {
            when (it) {
                is ClickState.ItemClicked -> {
                  /*  navigate(
                        WorkerFragmentDirections.actionFromWorkerFragmentToProjectsFragment(
                            workers[it.position].id, workers[it.position].fullName
                        )
                    )*/
                }

                is ClickState.EditUserAvatarClicked -> {
                    if (requireCameraPermissions() && requireWritePermissions()) {
                        workerDto = workers[it.position]
                        if (requireContext().isOnline)
                            takeCameraPictures()
                        else
                            EventOfCleanApp.send(EventErrorInternetConnection)
                    }

                }

                is ClickState.ShowLogClicked -> {
                   /* navigate(
                        WorkerFragmentDirections.actionFromWorkerFragmentToWorkLogFragment(
                            workers[it.position]
                        )
                    )*/
                }
            }

        }
        binding.userRecyclerView.adapter = adapter
        emptyResultHandle(workers.isEmpty())
    }
    /**
     * show empty result list
     * @param empty for handle empty view
     */
    private fun emptyResultHandle(empty: Boolean) {
        binding.workerProgressBar.visibility = GONE
        if (empty) {
            binding.userRecyclerView.visibility = GONE
            binding.emptyResultWorker.visibility = VISIBLE
        } else {
            binding.userRecyclerView.visibility = VISIBLE
            binding.emptyResultWorker.visibility = GONE

        }
    }
    /**
     * show camera view for take picture
     */
    private fun takeCameraPictures() {

        File(
            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "${System.currentTimeMillis()}" +
                    getString(R.string.extension_jpg)
        ).apply {
            if (parentFile?.exists() != true)
                parentFile?.mkdir()
            Timber.e(absolutePath)
            if (createNewFile()) {
                tempFilePath = absolutePath
                val uri =
                    FileProvider.getUriForFile(
                        requireContext(),
                        getString(R.string.file_provider_package),
                        this
                    )
                takeCameraPicture.launch(uri)
            }
        }
    }

}