@file:Suppress("unused", "DEPRECATION")

package com.borjali.presentation.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.RectF
import android.media.ExifInterface
import android.media.ExifInterface.*
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.borjali.domain.Constants.Companion.ISO_8601
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

/*val Context.isConnected: Boolean
    @SuppressLint("ObsoleteSdkInt")
    get() {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                val nw = connectivityManager.activeNetwork ?: return false
                val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
                when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        true
                    }
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        true

                    }
                    else -> false
                }
            }
            else -> {
                // Use depreciated methods only on older devices
                val nwInfo = connectivityManager.activeNetworkInfo ?: return false
                nwInfo.isConnected
            }
        }
    }*/


fun Context.setAppLocale(language: String): Context {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = resources.configuration
    config.setLocale(locale)
    config.setLayoutDirection(locale)
    return createConfigurationContext(config)
}

val Context.isOnline: Boolean
    @SuppressLint("MissingPermission")
    get() {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }

fun ImageView.setTin(color: Int) {
    this.setColorFilter(
        ContextCompat.getColor(
            this.context, color
        )
    )
}

fun String.formatAsDate(isCompletedYear: Boolean? = true): String {
    val dateFormat = SimpleDateFormat(
        if (isCompletedYear!!) "dd.MM.yyyy"
        else "dd MMM yy",
        Locale.getDefault()
    )
    val instance = Instant.parse(this)

    return try {
        dateFormat.format(Date.from(instance))
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
        ""
    }
}

@SuppressLint("SimpleDateFormat")
fun Date.toIsoString(): String {
    val dateFormat: DateFormat = SimpleDateFormat(ISO_8601)
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    return dateFormat.format(this)
}

@SuppressLint("SimpleDateFormat")
fun String.isoToDate(): Long {
    val format = SimpleDateFormat(ISO_8601)
    return (format.parse(this)?.time!! + TimeZone.getDefault().rawOffset)
}

@SuppressLint("SimpleDateFormat")
fun String.isoToHHMM(): String {
    val inputDateFormat = SimpleDateFormat( ISO_8601)
    inputDateFormat.timeZone = TimeZone.getTimeZone("UTC")

    val outputDateFormat = SimpleDateFormat("HH:mm")
    val date = inputDateFormat.parse(this)
    
    return date?.let { outputDateFormat.format(it) } ?: "--:--"
}

fun String.isoToDDMMYYYY(): String {
    val localTime = LocalTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
    val formatter = DateTimeFormatter.ofPattern("DD-mm-yyyy")
    return localTime.format(formatter)
}

fun Int.minutesToHHMM(): String {
    val hours = this / 60
    val minutes = this % 60
    return String.format("%02d:%02d", hours, minutes)
}

infix fun Int.percent(total: Int): Int {
    return this.times(100).div(total)
}

@SuppressLint("SimpleDateFormat")
fun Long.millisecondsToHHMMSS(): String? {
    val date = Date(this)
    val formatter: DateFormat = SimpleDateFormat("HH:mm:ss")
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(date)
}

@SuppressLint("SimpleDateFormat")
fun Long.millisecondsToMMSS(): String? {
    val date = Date(this)
    val formatter: DateFormat = SimpleDateFormat("MM:ss")
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(date)
}

@SuppressLint("SimpleDateFormat")
fun Long.millisecondsToHHMM(): String? {
    val date = Date(this)
    val formatter: DateFormat = SimpleDateFormat("HH:mm")
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(date)
}

fun Int.secondsToHHMM(): String {
    val hours = (this.div(60 * 60)).rem(24)
    val minutes = (this.div(60)).rem(60)
    val time = when {
        minutes < 10 && hours < 10 -> "0${hours}h 0${minutes}m"
        minutes < 10 -> "${hours}h 0${minutes}m"
        hours < 10 -> "0${hours}h ${minutes}m"
        else -> "${hours}h ${minutes}m "
    }
    return time
}


fun String.uriToFile(): File {
    val file = File(this.toUri().path.toString()).apply {
        createNewFile()
    }
    try {
        val out: OutputStream = FileOutputStream(file)
        out.write(ByteArrayOutputStream().toByteArray())
        out.flush()
        out.close()
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return file
}

fun Bitmap.scaleBitmapAndKeepRation(
    reqHeightInPixels: Int,
    reqWidthInPixels: Int
): Bitmap? {
    val matrix = Matrix()
    matrix.setRectToRect(
        RectF(0f, 0f, this.width.toFloat(), this.height.toFloat()),
        RectF(0f, 0f, reqWidthInPixels.toFloat(), reqHeightInPixels.toFloat()),
        Matrix.ScaleToFit.CENTER
    )
    return Bitmap.createBitmap(this, 0, 0, this.width, this.height, matrix, true)
}

fun File.bitmapToFile(bitmap: Bitmap): File {
    try {
        val exifData = ExifInterface(this.path)

        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 93 /*ignored for PNG*/, bos)
        val bitmapData = bos.toByteArray()
        val fos = FileOutputStream(this)
        fos.write(bitmapData)
        fos.flush()
        fos.close()

        exifData.copyToFile(this)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return this
}

fun ExifInterface.copyToFile(newFile: File) {
    val attributes = arrayOf(
        TAG_APERTURE,
        TAG_DATETIME,
        TAG_DATETIME_DIGITIZED,
        TAG_EXPOSURE_TIME,
        TAG_FLASH,
        TAG_FOCAL_LENGTH,
        TAG_GPS_ALTITUDE,
        TAG_GPS_ALTITUDE_REF,
        TAG_GPS_DATESTAMP,
        TAG_GPS_LATITUDE,
        TAG_GPS_LATITUDE_REF,
        TAG_GPS_LONGITUDE,
        TAG_GPS_LONGITUDE_REF,
        TAG_GPS_PROCESSING_METHOD,
        TAG_GPS_TIMESTAMP,
//            ExifInterface.TAG_IMAGE_LENGTH,
//            ExifInterface.TAG_IMAGE_WIDTH,
        TAG_ISO,
        TAG_MAKE,
        TAG_MODEL,
        TAG_ORIENTATION,
        TAG_SUBSEC_TIME,
        TAG_SUBSEC_TIME_DIG,
        TAG_SUBSEC_TIME_ORIG,
        TAG_WHITE_BALANCE
    )
    ExifInterface(newFile.path).let { newExif ->
        attributes.forEach { attr ->
            getAttribute(attr)?.let {
                newExif.setAttribute(attr, it)
            }
        }
        newExif.saveAttributes()
    }
}
