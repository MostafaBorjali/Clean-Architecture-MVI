// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(GradlePluginId.KTLINT_GRADLE) version CoreVersion.KTLINT_GRADLE
    id(GradlePluginId.DETEKT) version CoreVersion.DETEKT
    id(GradlePluginId.VERSIONS_PLUGIN) version CoreVersion.VERSIONS_PLUGIN
}
buildscript {
    repositories {
        google()
        mavenCentral()
        // Ktlint Gradle
        maven(GradlePluginId.KTLINT_MAVEN)
    }
    dependencies {
        classpath(GradleClasspath.ANDROID_GRADLE)
        classpath(kotlin(GradleClasspath.KOTLIN_PlUGIN, version = CoreVersion.KOTLIN))
        classpath(GradleClasspath.HILT)
        classpath(GradleClasspath.SAFE_ARGS)
        classpath(GradleClasspath.CRASHLYTICS_FIRE_BASE)
        classpath(GradleClasspath.KTLINT_CLASSPATH)
        classpath ("org.jetbrains.dokka:dokka-gradle-plugin:1.8.20")
        // classpath(GradleClasspath.GOOGLE_SERVICES)
    }
}
allprojects {
    repositories {
        google()
        mavenCentral()
    }

    // We want to apply ktlint at all project level because it also checks build gradle files
    plugins.apply(GradlePluginId.KTLINT_GRADLE)
    // Ktlint configuration for sub-projects
    ktlint {
        version.set(CoreVersion.KTLINT)
        verbose.set(true)
        android.set(true)
        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
        }
        disabledRules.addAll("no-wildcard-imports", "max-line-length")
        ignoreFailures.set(true)
        filter {
            exclude("**/generated/**")
        }
    }

    plugins.apply(GradlePluginId.DETEKT)

    detekt {
        config = files("${project.rootDir}/config/detekt.yml")
        parallel = true
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
