package constants

object AndroidConfig {

    const val COMPILE_SDK_VERSION = "android-UpsideDownCake"
    const val MIN_SDK_VERSION = 28
    const val TARGET_SDK_VERSION = 34
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0.1"

    const val CORE_NAMESPACE = "com.borjali.android"
    const val DATA_NAMESPACE = "com.borjali.data"
    const val DOMAIN_NAMESPACE = "com.borjali.domain"
    const val PRESENTATION_NAMESPACE = "com.borjali.presentation"

    const val ID = "com.borjali.android"
    const val TEST_INSTRUMENTATION_RUNNER = "com.borjali.android.util.CustomTestRunner"
}


interface BuildType {

    companion object {
        const val RELEASE = "release"
        const val DEBUG = "debug"
    }

    val isMinifyEnabled: Boolean
}

object BuildTypeDebug : BuildType {
    override val isMinifyEnabled = false
}

object BuildTypeRelease : BuildType {
    override val isMinifyEnabled = true
}