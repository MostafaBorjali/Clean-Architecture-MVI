import constants.AndroidConfig.CORE_NAMESPACE

plugins {
    id(GradlePluginId.ANDROID_APP)
    id(GradlePluginId.BASE_GRADLE_PLUGIN)
}

android{
    namespace = CORE_NAMESPACE
    flavorDimensions.add("CleanApp")
    productFlavors {
        create("Production"){
            dimension = "CleanApp"
            buildConfigField("String", "BASE_URL", "\"https://my-json-server.typicode.com/\"")
        }

        create("Stage"){
            dimension = "CleanApp"
            buildConfigField("String", "BASE_URL", "\"https://my-json-server.typicode.com/\"")
        }
    }
}


dependencies {
// Import Modules
    implementation(project(ModulesDependency.DATA))
    implementation(project(ModulesDependency.DOMAIN))
    implementation(project(ModulesDependency.PRESENTATION))


    // Rest Api
    implementation(LibraryDependency.retrofit)
    implementation(LibraryDependency.retrofit_gson)
    implementation(LibraryDependency.okhttp)
    // Log Tools
    implementation(LibraryDependency.stetho)
    implementation(LibraryDependency.stetho_okhttp3)
    implementation(LibraryDependency.logging_interceptor)
    implementation(LibraryDependency.multi_dex)
    // Room
    implementation(LibraryDependency.room_runtime)
    implementation(LibraryDependency.room_ktx)
    kapt(LibraryDependency.AnnotationProcessing.room_compiler)
    // AppMetrica SDK.
    implementation(LibraryDependency.yandex)
    implementation("com.squareup:javapoet:1.13.0")
    addAndroidTestDependencies()

}
