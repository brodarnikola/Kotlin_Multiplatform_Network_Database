
plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android")
}

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    implementation("androidx.core:core-ktx:1.3.1")
    implementation("androidx.recyclerview:recyclerview:1.1.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")
    implementation("androidx.annotation:annotation:1.1.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")

    val composeVersion = "1.0.0-beta03"
    implementation("androidx.compose.ui:ui:${composeVersion}")
    implementation("androidx.compose.material:material:${composeVersion}")
    implementation("androidx.compose.ui:ui-tooling:${composeVersion}")
    implementation("androidx.compose.foundation:foundation:${composeVersion}")
    implementation("androidx.compose.compiler:compiler:${composeVersion}")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0-alpha03")
    implementation("androidx.activity:activity-compose:1.3.0-alpha03")
    implementation("androidx.navigation:navigation-compose:1.0.0-alpha08")

    // Needed for viewmodel to do constructor injection
    val hiltVersion = "2.35"
    implementation("androidx.hilt:hilt-navigation:1.0.0-alpha03")
    kapt("com.google.dagger:hilt-compiler:${hiltVersion}")
    implementation("com.google.dagger:hilt-android:${hiltVersion}")


    implementation("com.google.accompanist:accompanist-coil:0.7.0")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.1.1")

}

android {
    compileSdkVersion(30)
    defaultConfig {
        applicationId = "com.example.firstmultiplatformproject.android"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        useIR = true
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.0-beta03"
    }

}