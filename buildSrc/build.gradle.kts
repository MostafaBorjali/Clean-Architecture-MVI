plugins {
    `kotlin-dsl`
}
//The kotlin-dsl plugin requires a repository to be declared
repositories {
    google()
    mavenCentral()
}
gradlePlugin {
    plugins {
        register("base-gradle-plugin") {
            id = "base-gradle-plugin"
            implementationClass = "plugin.CleanAppGradlePlugin"
        }
    }
}
dependencies {
    /* Depend on the android gradle plugin, since we want to access it in our plugin */
    implementation("com.android.tools.build:gradle:8.0.2")
    /* Depend on the kotlin plugin, since we want to access it in our plugin */
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.20")
}