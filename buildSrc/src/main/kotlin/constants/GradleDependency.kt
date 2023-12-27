object CoreVersion{
    const val KOTLIN = "1.8.20"
    const val NAVIGATION = "2.5.3"
    const val ANDROID_GRADLE = "8.0.2"
    const val KTLINT_GRADLE = "10.0.0"
    const val KTLINT = "0.40.0"
    const val DETEKT = "1.16.0-RC1"
    const val VERSIONS_PLUGIN = "0.46.0"
    const val PLAY_SERVICES = "4.3.15"
    const val crashlytics_gradle = "2.9.5"
    const val HILT_DAGGER = "2.38.1"

}

object GradlePluginId {
    const val ANDROID_APP = "com.android.application"
    const val ANDROID_LIB  = "com.android.library"
    const val ANDROID = "kotlin-android"
    const val KAPT = "kotlin-kapt"
    const val HILT = "dagger.hilt.android.plugin"
    const val SAFE_ARGS = "androidx.navigation.safeargs"
    const val BASE_GRADLE_PLUGIN = "base-gradle-plugin"
    const val KTLINT_GRADLE = "org.jlleitschuh.gradle.ktlint"
    const val KTLINT_MAVEN =  "https://plugins.gradle.org/m2/"
    const val DETEKT = "io.gitlab.arturbosch.detekt"
    const val VERSIONS_PLUGIN = "com.github.ben-manes.versions"
}

object GradleClasspath {
    const val KOTLIN_PlUGIN = "gradle-plugin"
    const val ANDROID_GRADLE = "com.android.tools.build:gradle:${CoreVersion.ANDROID_GRADLE}"
    const val SAFE_ARGS = "androidx.navigation:navigation-safe-args-gradle-plugin:${CoreVersion.NAVIGATION}"
    const val  KTLINT_CLASSPATH ="org.jlleitschuh.gradle:ktlint-gradle:${CoreVersion.KTLINT_GRADLE}"
    const val GOOGLE_SERVICES = "com.google.gms:google-services:${CoreVersion.PLAY_SERVICES}"
    const val HILT = "com.google.dagger:hilt-android-gradle-plugin:${CoreVersion.HILT_DAGGER}"
    const val CRASHLYTICS_FIRE_BASE =
        "com.google.firebase:firebase-crashlytics-gradle:${CoreVersion.crashlytics_gradle}"
}


