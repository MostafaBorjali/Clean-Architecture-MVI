package com.borjali.domain

/**
 * Domain Layer constants.
 */
class Constants {
    companion object {
        const val DEFAULT_DISK_CACHE_SIZE: Long = 1024 * 1024 * 25000L
        const val SPLASH_DELAY = 2500L
        const val FILE = "file"
        const val BIOZOON = "biozoon/app/"
        const val IS_LOGGED_IN = "isLoggedIn"
        const val LOCALE = "locale"
        const val GERMANY = "de"
        const val ENGLISH = "en"
        const val ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

        // -----------------------------MIMETYPE-----------------------
        const val IMAGE_JPG = "image/jpeg"
        const val TEXT_PLAIN = "text/plain"

        // -----------------------------ERROR-----------------------
        const val MESSAGE = "message"
        const val NETWORK_ERROR = "Network error! Please make sure internet connection is available."
        const val NETWORK_ERROR_TIMEOUT = "Network timeout"
        const val UNKNOWN_ERROR = "Unknown error"
        const val NETWORK_ERROR_DE = "Netzwerk fehler! Bitte stellen Sie sicher, dass eine Internet verbindung verfügbar ist."
        const val NETWORK_ERROR_TIMEOUT_DE = "Netzwerk-Timeout"
        const val UNKNOWN_ERROR_DE= "Unbekannter Fehler"

    }
}
