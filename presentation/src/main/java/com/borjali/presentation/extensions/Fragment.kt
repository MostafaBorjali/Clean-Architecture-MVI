@file:Suppress("unused")

package com.borjali.presentation.extensions

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.borjali.domain.utils.MessageType
import com.borjali.presentation.ui.component.SnackBarMessage
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

fun Fragment.openLink(url: String) {
    val i = Intent(Intent.ACTION_VIEW)
    i.data = Uri.parse(url)
    startActivity(i)
}

fun Fragment.navigate(fragment: Int, arg: Bundle? = null) {
    if (arg != null) {
        findNavController().navigate(fragment, arg)
    } else {
        findNavController().navigate(fragment)
    }
}

fun Fragment.navigate(direction: NavDirections) {
    findNavController().navigate(direction)
}

fun Fragment.back() {
    findNavController().popBackStack()
}

fun Fragment.snackMessage(
    message: String,
    messageType: MessageType
) {
    view?.let {
        SnackBarMessage().show(
            context = context,
            message = message,
            messageType = messageType,
            root = it
        )
    }
}

fun Fragment.uriToFile(uri: Uri): File {
    val storageDir = requireContext().externalCacheDir
    val file = File(
        storageDir, "${System.currentTimeMillis()}"
    )
        .apply {
            createNewFile()
        }

    try {
        val input = requireContext().contentResolver.openInputStream(uri)

        val out: OutputStream = FileOutputStream(file)

        FileOutputStream(file).use { output ->
            val buffer = ByteArray(4 * 1024) // or other buffer size
            var read: Int
            while (input?.read(buffer).also { read = it!! } != -1) {
                output.write(buffer, 0, read)
            }
            output.flush()
        }
        input?.close()
        out.close()
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return file
}

fun Fragment.isLocationPermissionGranted(): Boolean {
    return if (ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            100
        )
        false
    } else {
        true
    }
}

fun Fragment.requireCameraPermissions(): Boolean {
    return if (Manifest.permission.CAMERA.let {
        ContextCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
    }
    ) true
    else {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.CAMERA), 100
        )
        false
    }
}
fun Fragment.requireWritePermissions(): Boolean {
    if (Build.VERSION.SDK_INT >= 33) {
        return true
    } else {
        return if (Manifest.permission.WRITE_EXTERNAL_STORAGE.let {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    it
                ) == PackageManager.PERMISSION_GRANTED
            }) true
        else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 100
            )
            false
        }
    }
}
fun Fragment.requireReadStoragePermissions(): Boolean {
    if (Build.VERSION.SDK_INT >= 33) {
        return true
    } else {
        return if (Manifest.permission.READ_EXTERNAL_STORAGE.let {
            ContextCompat.checkSelfPermission(
                    requireContext(),
                    it
                ) == PackageManager.PERMISSION_GRANTED
        }
        ) true
        else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 100
            )
            false
        }
    }
}

fun Fragment.requireVoiceRecordPermissions(): Boolean {
    if (Build.VERSION.SDK_INT >= 29) {
        return if (Manifest.permission.RECORD_AUDIO.let {
            ContextCompat.checkSelfPermission(
                    requireContext(),
                    it
                ) == PackageManager.PERMISSION_GRANTED
        }
        ) true
        else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.RECORD_AUDIO
                ),
                100
            )
            false
        }
    } else {
        return if (Manifest.permission.WRITE_EXTERNAL_STORAGE.let {
            ContextCompat.checkSelfPermission(
                    requireContext(),
                    it
                ) == PackageManager.PERMISSION_GRANTED
        } && Manifest.permission.RECORD_AUDIO.let {
                ContextCompat.checkSelfPermission(
                        requireContext(),
                        it
                    ) == PackageManager.PERMISSION_GRANTED
            }
        ) true
        else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO
                ),
                100
            )
            false
        }
    }
}
