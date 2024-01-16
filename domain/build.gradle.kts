plugins {
    id(GradlePluginId.ANDROID_LIB)
    id(GradlePluginId.BASE_GRADLE_PLUGIN)
   // id("org.jetbrains.dokka")
}
android{
    namespace = constants.AndroidConfig.DOMAIN_NAMESPACE
}
dependencies {
    implementation(LibraryDependency.retrofit_gson)
}
