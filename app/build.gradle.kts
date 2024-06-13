plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id ("com.google.dagger.hilt.android")
    id ("com.google.devtools.ksp")
    alias(libs.plugins.googleGmsGoogleServices)
}

android {
    namespace = "com.makaraya.movapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.makaraya.movapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
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
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Splash API
    implementation ("androidx.core:core-splashscreen:1.0.1")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Pager and Indicators - Accompanist
    implementation ("com.google.accompanist:accompanist-pager:0.34.0")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.34.0")

    // DataStore Preferences
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    implementation ("androidx.compose.material:material-icons-extended:1.6.7")
    implementation ("androidx.activity:activity-compose:1.9.0")
    implementation ("androidx.compose.material3:material3:1.2.1")
    implementation ("androidx.compose.animation:animation:1.6.7")
    implementation ("androidx.compose.ui:ui-tooling:1.6.7")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.1")
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    //Dagger-Hilt
    implementation ("com.google.dagger:hilt-android:2.49")
    ksp ("com.google.dagger:hilt-android-compiler:2.49")
    ksp ("androidx.hilt:hilt-compiler:1.2.0")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")

    // firebase
    // google auth
    implementation("com.google.android.gms:play-services-auth:21.2.0")
}