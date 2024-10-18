plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.weather"
    compileSdk = 34
    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        applicationId = "com.example.weather"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "BASE_URL", "\"https://api.openweathermap.org/data/2.5/\"")
        buildConfigField("String", "API_KEY", "\"e4a4f41c717a1f73bf15fa5807454d53\"")
        buildConfigField("String", "GEOCODE_URL", "\"http://api.openweathermap.org/geo/1.0/\"")
        buildConfigField("String", "ICON_URL","\"https://openweathermap.org/img/wn/\"")

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.runtime.android)
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("com.google.accompanist:accompanist-permissions:0.32.0")

    implementation(libs.material.v180)
    implementation(platform("androidx.compose:compose-bom:2023.01.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.01.00"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.material3)
    implementation(libs.material3)
    implementation("androidx.compose.material3:material3-window-size-class:1.3.0")
    // koin
    implementation("io.insert-koin:koin-androidx-compose:3.4.2")
    // networking - retrofit
    api("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //GMS
    implementation ("com.google.android.gms:play-services-location:21.3.0")
    //Coil
    implementation(libs.coil.compose)
    implementation (libs.coil.svg)
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    // JUnit
    testImplementation("junit:junit:4.13.2")
    // Mockito for Kotlin
    testImplementation("org.mockito:mockito-core:4.2.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("org.mockito:mockito-inline:4.11.0")
    // Coroutine testing
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("io.mockk:mockk:1.13.5")


}