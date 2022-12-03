@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.symbolProcessing)
}

android {
    namespace = "com.antoine.newweatherapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.antoine.newweatherapp"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
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

    implementation(libs.coroutines)
    implementation(libs.retrofitCore)
    implementation(libs.retrofitGson)
    implementation(libs.okhttpInterceptor)
    implementation(libs.okhttp)
    implementation(libs.navigationUiKtx)
    implementation(libs.navigationFragment)
    implementation(libs.koin)
    implementation(libs.constraintLayout)
    implementation(libs.coreKtx)
    implementation(libs.timber)
    implementation(libs.recyclerView)
    implementation(libs.appCompat)

    implementation(libs.roomRuntime)
    implementation(libs.roomKtx)
    ksp(libs.roomCompiler)

    implementation(libs.testCoroutines)
    implementation(libs.mockk)
    implementation(libs.junit)
    implementation(libs.material)
}