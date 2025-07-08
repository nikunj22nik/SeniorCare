plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)         //  plugin from toml
    alias(libs.plugins.kotlin.kapt)  //  kapt plugin from toml
}


android {
    namespace = "com.bussiness.composeseniorcare"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.bussiness.composeseniorcare"
        minSdk = 24
        targetSdk = 35
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
        debug {
            val BASE_URL = project.property("BASE_URL")
            buildConfigField("String", "BASE_URL", "${BASE_URL}")
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
        dataBinding = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += setOf(
                "META-INF/INDEX.LIST",
                "META-INF/DEPENDENCIES",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/license.txt",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
                "META-INF/ASL2.0",
                "META-INF/io.netty.versions.properties"
            )
        }
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
    implementation(libs.androidx.animation.core.lint)
    implementation(libs.firebase.appdistribution.gradle)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //navigation
    implementation(libs.androidx.navigation.compose)
    implementation (libs.androidx.material.icons.extended)
    //material3
    implementation(libs.material3)
    //carousel
    implementation(libs.androidx.foundation)
    implementation(libs.ui)
    //coil (image loading)
    implementation(libs.coil.compose)
    // Pager (Accompanist)
    implementation (libs.accompanist.pager.v0360)
    implementation (libs.accompanist.pager.indicators.v0360)
    implementation (libs.androidx.material) // For legacy components like RangeSlider
    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    // Optional: Hilt + Jetpack Compose
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.androidx.hilt.compiler)
    // Retrofit + OkHttp
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.logging.interceptor)
    // Network monitoring
    implementation (libs.androidx.lifecycle.runtime.ktx.v291)
    implementation (libs.androidx.runtime.livedata)
    //loader
    implementation (libs.lottie.compose)


}