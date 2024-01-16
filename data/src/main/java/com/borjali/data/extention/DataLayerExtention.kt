

package com.borjali.data.extention

import android.annotation.SuppressLint
import com.borjali.domain.Constants
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * this function convert Date to iso 8601 string format
 */
@SuppressLint("SimpleDateFormat")
fun Date.toIsoString(): String {
    val dateFormat: DateFormat = SimpleDateFormat(Constants.ISO_8601)
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    return dateFormat.format(this)
}
