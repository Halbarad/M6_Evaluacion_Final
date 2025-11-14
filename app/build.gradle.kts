plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
<<<<<<< HEAD
}

android {
    namespace = "com.aplicaciones_android.ae2_abpro1___grupo_1"
=======
    id("androidx.navigation.safeargs")
    id("org.jetbrains.kotlin.plugin.parcelize")
}

android {
    namespace = "cl.unab.m6_ae2abp"
>>>>>>> 947a498a266eca5417002272fdbdc3690f57dbbc
    compileSdk {
        version = release(36)
    }

    defaultConfig {
<<<<<<< HEAD
        applicationId = "com.aplicaciones_android.ae2_abpro1___grupo_1"
=======
        applicationId = "cl.unab.m6_ae2abp"
>>>>>>> 947a498a266eca5417002272fdbdc3690f57dbbc
        minSdk = 27
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
<<<<<<< HEAD
=======

    buildFeatures {
        viewBinding = true
    }

>>>>>>> 947a498a266eca5417002272fdbdc3690f57dbbc
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
<<<<<<< HEAD
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Retrofit + Gson
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // OkHttp logging
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    // Lifecycle (ViewModel + LiveData)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // RecyclerView & CardView & SwipeRefresh
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.3")
=======
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // ViewMModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    // Lifecycle Runtime
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    // Fragment KTX (para usar by viewModels())
    implementation("androidx.fragment:fragment-ktx:1.8.9")
    // Courutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Convertir JSON a objetos Kotlin
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // AndroidX Test
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestImplementation("androidx.navigation:navigation-testing:2.7.7")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")

    // MockWebServer
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
>>>>>>> 947a498a266eca5417002272fdbdc3690f57dbbc
}