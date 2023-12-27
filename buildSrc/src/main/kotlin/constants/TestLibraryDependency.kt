object TestLibraryDependency{
    object Version{
        const val JUNIT = "4.13.2"
        const val JUNIT_ANDROID = "1.1.3"
        const val ESPRESSO = "3.3.0"
        const val HILT = "2.44"
        const val MOCKITO = "5.0.0"
        const val MOCKITO_ANDROID = "3.11.2"
        const val MOCK_WEB_SERVER = "4.7.2"
        const val ROBOLECTRIC = "4.5.1"
        const val COROUTINES = "1.5.1"
        const val TRUTH = "1.0.1"
        const val ARCH = "2.1.0"
        const val RUNNER_ANDROID = "1.5.1"
        const val NAVIGATION = "2.3.5"
        const val FRAGMENT = "1.5.5"

    }

    const val JUNIT = "junit:junit:${Version.JUNIT}"
    const val JUNIT_ANDROID = "androidx.test.ext:junit:${Version.JUNIT_ANDROID}"
    const val ESPRESSO = "androidx.test.espresso:espresso-core:${Version.ESPRESSO}"
    const val ESPRESSO_CONTRIP = "androidx.test.espresso:espresso-contrib:${Version.ESPRESSO}"
    const val ESPRESSO_IDLING_RESOURCE = "androidx.test.espresso:espresso-idling-resource:${Version.ESPRESSO}"
    const val HILT = "com.google.dagger:hilt-android-testing:${Version.HILT}"
    const val MOCKITO = "org.mockito:mockito-core:${Version.MOCKITO}"
    const val MOCKITO_ANDROID = "org.mockito:mockito-android:${Version.MOCKITO_ANDROID}"
    const val MOCK_WEB_SERVER = "com.squareup.okhttp3:mockwebserver:${Version.MOCK_WEB_SERVER}"
    const val ROBOLECTRIC = "org.robolectric:robolectric:${Version.ROBOLECTRIC}"
    const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.COROUTINES}"
    const val TRUTH = "com.google.truth:truth:${Version.TRUTH}"
    const val ARCH = "androidx.arch.core:core-testing:${Version.ARCH}"
    const val RUNNER_ANDROID = "androidx.test:runner:${Version.RUNNER_ANDROID}"
    const val NAVIGATION = "androidx.navigation:navigation-testing:${Version.NAVIGATION}"
    const val FRAGMENT = "androidx.fragment:fragment-testing:${Version.FRAGMENT}"



    /*

    androidTestImplementation 'com.linkedin.dexmaker:dexmaker-mockito:2.28.3'

  */



}