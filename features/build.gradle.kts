plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.feature_login"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        aidl = false
        buildConfig = false
        renderScript = false
        shaders = false
    }

}


dependencies {

    implementation(project(":domain"))  // Bergantung pada modul domain
    implementation(project(":data"))    // Bergantung pada modul data
    implementation(project(":core-ui")) // Bergantung pada module core-ui




    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    implementation(libs.litert.support.api)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.transition)
    implementation(libs.androidx.runtime.android)
    implementation(libs.androidx.core)
    implementation(libs.google.firebase.auth.ktx)
    implementation(libs.androidx.foundation.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Asyncronous
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)


    // ViewModel & LiveData
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.livedata.ktx)


    //datastore
    implementation(libs.androidx.datastore.preferences)

    //lottie
    implementation(libs.lottie)

    //coil
    implementation(libs.coil.compose)

    //pagination
    implementation(libs.androidx.paging.common.ktx)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)

    //carousel
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators.v0320)

    //navigation
    implementation(libs.androidx.navigation.compose)
    //Timber
    implementation(libs.timber)

    implementation(libs.androidx.material.icons.extended)

    //Shimmer
    implementation(libs.accompanist.placeholder.material)

    // For tabs and pager (now in androidx)
    implementation (libs.androidx.foundation)

    implementation(libs.androidx.compose.material3)

}