plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.vanshpunia.melody"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.vanshpunia.melody"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation ("com.google.firebase:firebase-bom:33.1.2")

    implementation ("com.github.bumptech.glide:glide:4.16.0")

    implementation ("com.squareup.picasso:picasso:2.8")
    
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    implementation("com.google.android.material:material:1.x.x")

    implementation("androidx.media3:media3-exoplayer:1.4.0")
    implementation("androidx.media3:media3-exoplayer-dash:1.4.0")
    implementation("androidx.media3:media3-ui:1.4.0")

    implementation("androidx.media3:media3-ui:1.0.0-rc01")

}