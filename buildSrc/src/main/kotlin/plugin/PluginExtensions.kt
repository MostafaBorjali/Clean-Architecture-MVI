package plugin

import com.android.build.gradle.BaseExtension
import constants.AndroidConfig
import constants.BuildType
import org.gradle.api.Project
import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.text.SimpleDateFormat
import java.util.*


internal fun Project.configureDefaultPlugins() {
    plugins.apply(GradlePluginId.ANDROID)
    plugins.apply(GradlePluginId.KAPT)
    plugins.apply(GradlePluginId.HILT)
}

private typealias AndroidBaseExtension = BaseExtension

@Suppress("SimpleDateFormat")
internal fun Project.configureAndroidApp() =  this.extensions.getByType<AndroidBaseExtension>().run {
    compileSdkVersion(33)
    defaultConfig {
        minSdk = AndroidConfig.MIN_SDK_VERSION
        targetSdk = 33
        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME
       // testInstrumentationRunner = "com.borjali.app.util.HiltTestRunner"
        multiDexEnabled = true
        val date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        setProperty("archivesBaseName", "CleanApp($date)${versionName}")
    }
    buildTypes {
        getByName(BuildType.DEBUG) {
            isTestCoverageEnabled = true
            isMinifyEnabled = false

        }
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures.buildConfig = true
    dataBinding.enable = true
    buildFeatures.viewBinding = true

    project.tasks.withType(KotlinCompile::class.java).configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }

}

internal fun Project.configureDependencies() = this.dependencies {
    add("implementation", LibraryDependency.kotlin_standard_library)
    add("implementation", LibraryDependency.ktx)
    //Hilt
    add("implementation", LibraryDependency.hilt)
    add("kapt", LibraryDependency.AnnotationProcessing.hilt_dagger_compiler)
    add("androidTestImplementation", TestLibraryDependency.HILT)
   //add("kaptAndroidTest", LibraryDependency.AnnotationProcessing.hilt_dagger_compiler)
    // --kotlin coroutines
    add("implementation", LibraryDependency.kotlin_coroutines)
    add("implementation", LibraryDependency.kotlin_coroutines_android)

    add("implementation", LibraryDependency.timber)

}
