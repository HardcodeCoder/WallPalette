plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.jetbrains.kotlin)
}

android {
    namespace = "com.hardcodecoder.wallpalette"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.hardcodecoder.wallpalette"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "0.0.1"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    tasks.withType(Test::class) {
        useJUnitPlatform()
    }
}

dependencies {
    // Core components and essential extensions
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.viewmodel.ktx)
    implementation(libs.activity.compose)
    implementation(libs.androidx.viewmodel.compose)

    // Android compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Coroutines and utils
    implementation(libs.kotlin.coroutine)
    implementation(libs.androidx.collection)

    // Coil - image loading library
    implementation(platform(libs.coil.bom))
    implementation(libs.coil)
    implementation(libs.coil.compose)

    // Koin - dependency injection library
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    // Retrofit - for network io
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // Test frameworks
    testImplementation(libs.junit5)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlin.coroutine.test)
}