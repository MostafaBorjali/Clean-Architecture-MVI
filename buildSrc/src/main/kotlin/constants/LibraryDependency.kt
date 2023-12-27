object LibraryDependency {
    object AnnotationProcessing {
        const val hilt_dagger_compiler =
            "com.google.dagger:hilt-android-compiler:${Version.hilt_dagger}"
        const val hilt_android_compiler = "androidx.hilt:hilt-compiler:${Version.hilt}"
        const val room_compiler = "androidx.room:room-compiler:${Version.room}"
        const val glide_compiler = "com.github.bumptech.glide:compiler:${Version.glide}"
        //const val lifecycle_compiler = "androidx.lifecycle:lifecycle-compiler:${Version.lifecycle}"
    }
    object Version {
        const val COR_KTX = "1.10.1"
        const val multi_dex = "2.0.1"
        const val appcompat = "1.6.1"
        const val material_design = "1.9.0"
        const val constraint_layout = "2.1.4"
        const val legacy_support_v4 = "1.0.0"
        const val hilt_dagger = "2.44"
        const val hilt = "1.0.0"
        const val retrofit2 = "2.9.0"
        const val converter_moshi = "2.9.0"
        const val okhttp3 = "4.11.0"
        const val lifecycle = "2.6.1"
        const val lifecycle_extensions = "2.2.0"
        const val room = "2.5.0"
        const val glide = "4.10.0"
        const val play_services_location = "21.0.1"
        const val nav_components = "2.5.3"
        const val coroutines_core = "1.7.1"
        const val coroutines_android = "1.7.1"
        const val security = "1.0.0"
        const val stetho = "1.6.0"
        const val timber = "5.0.1"
        const val yandex = "5.3.0"
        const val exo_player = "2.18.7"
        /* const val firebase_bom = "29.3.1"
        const val firebase_analytics = "20.1.2"
        const val firebase_crashlytics = "18.2.9"
        const val exifinterface = "1.3.3"
        const val desugar_jdk_libs = "1.1.5"
         const val converter_scalars = "2.1.0"
        */
    }


    //kiel
   // const val KIEL = "me.ibrahimyilmaz:kiel:${Version.KIEL}"

    const val appcompat = "androidx.appcompat:appcompat:${Version.appcompat}"
    const val constraint_layout =
        "androidx.constraintlayout:constraintlayout:${Version.constraint_layout}"
    const val material_design = "com.google.android.material:material:${Version.material_design}"
    const val legacy_support_v4 = "androidx.legacy:legacy-support-v4:${Version.legacy_support_v4}"
    const val kotlin_standard_library = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${CoreVersion.KOTLIN}"
    const val ktx = "androidx.core:core-ktx:${Version.COR_KTX}"
    const val multi_dex = "androidx.multidex:multidex:${Version.multi_dex}"
    const val hilt = "com.google.dagger:hilt-android:${Version.hilt_dagger}"
    const val play_services_location = "com.google.android.gms:play-services-location:${Version.play_services_location}"
    /*const val hilt_lifecycle_viewmodel = "androidx.hilt:hilt-lifecycle-viewmodel:${Version.hilt}"
    const val exifinterface = "androidx.exifinterface:exifinterface:${Version.exifinterface}"
    const val desugar_jdk_libs = "com.android.tools:desugar_jdk_libs:${Version.desugar_jdk_libs}"
    const val swipe_refresh_layout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipe_refresh_layout}"*/

    //retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit2}"
    const val retrofit_gson = "com.squareup.retrofit2:converter-gson:${Version.retrofit2}"
    const val retrofit_moshi = "com.squareup.retrofit2:converter-moshi:${Version.converter_moshi}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Version.okhttp3}"
    const val logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Version.okhttp3}"

    //Lifecycle
//    const val lifecycle_extensions =
//        "androidx.lifecycle:lifecycle-extensions:${Version.lifecycle_extensions}"
    const val lifecycle_viewmodel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifecycle}"
    const val lifecycle_runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifecycle}"
    const val lifecycle_livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Version.lifecycle}"
    const val lifecycle_reactive_streams =
        "androidx.lifecycle:lifecycle-reactivestreams:${Version.lifecycle}"

    // room
    const val room_runtime = "androidx.room:room-runtime:${Version.room}"
    const val room_ktx = "androidx.room:room-ktx:${Version.room}"

    //Glide
    const val glide = "com.github.bumptech.glide:glide:${Version.glide}"

    // navigation
    const val navigation_fragment =
        "androidx.navigation:navigation-fragment-ktx:${Version.nav_components}"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Version.nav_components}"
    /*
    const val navigation_runtime =
        "androidx.navigation:navigation-runtime:${Version.nav_components}"
    const val navigation_dynamic =
        "androidx.navigation:navigation-dynamic-features-fragment:${Version.nav_components}"
        */

    // firebase
   /*
    const val firebase_bom = "com.google.firebase:firebase-bom:${Version.firebase_bom}"
    const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
    const val analytics = "com.google.firebase:firebase-analytics-ktx"
    */

    // coroutines
    const val kotlin_coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutines_core}"
    const val kotlin_coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutines_android}"
    const val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:${CoreVersion.KOTLIN}"
    // val kotlin_coroutines_play_services = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.coroutines_play_services}"

    // Log Tools
    const val stetho = "com.facebook.stetho:stetho:${Version.stetho}"
    const val stetho_okhttp3 = "com.facebook.stetho:stetho-okhttp3:${Version.stetho}"
    const val timber = "com.jakewharton.timber:timber:${Version.timber}"
    const val yandex = "com.yandex.android:mobmetricalib:${Version.yandex}"

    // security
    const val security = "androidx.security:security-crypto:${Version.security}"
    const val exo_player = "com.google.android.exoplayer:exoplayer:${Version.exo_player}"

    /* val play_core = "com.google.android.play:core:${Versions.play_core}"
     val leak_canary = "com.squareup.leakcanary:leakcanary-android:${Versions.leak_canary}"
     val firebase_firestore = "com.google.firebase:firebase-firestore-ktx:${Versions.firebase_firestore}"
     val firebase_auth = "com.google.firebase:firebase-auth:${Versions.firebase_auth}"
     val firebase_analytics = "com.google.firebase:firebase-analytics-ktx:${Versions.firebase_analytics}"
     val firebase_crashlytics = "com.google.firebase:firebase-crashlytics:${Versions.firebase_crashlytics}"*/

}