plugins {
    id(GradlePluginId.ANDROID_LIB)
    id(GradlePluginId.BASE_GRADLE_PLUGIN)
    //id("org.jetbrains.dokka")
}
android {
    namespace = constants.AndroidConfig.DATA_NAMESPACE
}
dependencies {
    implementation(LibraryDependency.glide)
    kapt(LibraryDependency.AnnotationProcessing.glide_compiler)
    implementation(LibraryDependency.kotlin_reflect)
    implementation(LibraryDependency.room_runtime)
    implementation(LibraryDependency.room_ktx)
    kapt(LibraryDependency.AnnotationProcessing.room_compiler)
    implementation(LibraryDependency.retrofit)
    implementation(LibraryDependency.retrofit_gson)
    implementation(LibraryDependency.retrofit_moshi)
    implementation(LibraryDependency.okhttp)
    implementation(LibraryDependency.security)
    implementation(LibraryDependency.yandex)

    implementation(project(ModulesDependency.DOMAIN))

    addTestDependencies()
    addAndroidTestDependencies()
}
