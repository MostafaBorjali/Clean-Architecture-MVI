import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

/*
Define common dependencies, so they can be easily updated across feature modules
 */
fun DependencyHandler.addTestDependencies() {
    testImplementation(TestLibraryDependency.JUNIT)
  //  testImplementation(TestLibraryDependency.MOCKITO)
   // testImplementation(TestLibraryDependency.ROBOLECTRIC)
    testImplementation(TestLibraryDependency.COROUTINES)
    testImplementation(TestLibraryDependency.ARCH)
    testImplementation(TestLibraryDependency.MOCK_WEB_SERVER)
}

fun DependencyHandler.addAndroidTestDependencies() {
    androidTestImplementation(TestLibraryDependency.RUNNER_ANDROID)
    androidTestImplementation(TestLibraryDependency.TRUTH)
    androidTestImplementation(TestLibraryDependency.ARCH)
    androidTestImplementation(TestLibraryDependency.ESPRESSO)
    androidTestImplementation(TestLibraryDependency.ESPRESSO_CONTRIP)
   // androidTestImplementation(TestLibraryDependency.ROBOLECTRIC)
    implementation(TestLibraryDependency.ESPRESSO_IDLING_RESOURCE)
   // androidTestImplementation(TestLibraryDependency.MOCKITO_ANDROID)
    androidTestImplementation(TestLibraryDependency.JUNIT_ANDROID)
    androidTestImplementation(TestLibraryDependency.COROUTINES)
    androidTestImplementation (TestLibraryDependency.NAVIGATION)
  //  androidTestImplementation (TestLibraryDependency.FRAGMENT)
}

/*
 * These extensions mimic the extensions that are generated on the fly by Gradle.
 * They are used here to provide above dependency syntax that mimics Gradle Kotlin DSL syntax in module\build.gradle.kts files.
 */
fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

private fun DependencyHandler.api(dependencyNotation: Any): Dependency? =
    add("api", dependencyNotation)

fun DependencyHandler.kapt(dependencyNotation: Any): Dependency? =
    add("kapt", dependencyNotation)

private fun DependencyHandler.testImplementation(dependencyNotation: Any): Dependency? =
    add("testImplementation", dependencyNotation)

private fun DependencyHandler.androidTestImplementation(dependencyNotation: Any): Dependency? =
    add("androidTestImplementation", dependencyNotation)
