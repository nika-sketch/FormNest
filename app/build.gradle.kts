plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.formnest"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.formnest"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "FORM_NEST_BASE_URL", "\"https://mocki.io/v1/\"")
            buildConfigField(
                "String",
                "FORM_NEST_ENDPOING",
                "\"f118b9f0-6f84-435e-85d5-faf4453eb72a\""
            )
        }
        release {
            buildConfigField("String", "FORM_NEST_BASE_URL", "\"https://mocki.io/v1/\"")
            buildConfigField(
                "String",
                "FORM_NEST_ENDPOING",
                "\"f118b9f0-6f84-435e-85d5-faf4453eb72a\""
            )
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Networking dependencies
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logginginterceptor)
    implementation(libs.moshi.kotlin)
    implementation(libs.converter.moshi)

    //ViewModel/Activity
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

}