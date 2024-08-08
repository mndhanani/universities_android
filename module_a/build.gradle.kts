plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.kapt)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "com.android.universities"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.android.universities"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        // Enables the data binding.
        dataBinding = true
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
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    // Modules
    implementation(project(":module_b"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)

    // Lifecycle aware async programming
    implementation(libs.androidx.lifecycle)

    // Hilt for dependency injection
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    // Retrofit for network calls
    implementation(libs.retrofit)
    implementation(libs.google.code.gson)
    implementation(libs.retrofit.gson.converter)

    // Room Database for local storage
    implementation(libs.room)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Mocking framework for unit tests
    testImplementation(libs.mockito)

    // Kotlin Coroutines test
    testImplementation(libs.kotlin.junit)
    testImplementation(libs.coroutines.test)
}