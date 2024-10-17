plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services").version("4.4.2")
}

android {
    signingConfigs {
        create("release") {
            storeFile = file("/Users/youruser/keystores/upload-keystore.jks")
            storePassword = "mykeystore"
            keyPassword = "mykeystore"
            keyAlias = "android"
        }
    }
    namespace = "com.example.carebridge"
    compileSdk = 34

    packagingOptions {
        resources.excludes.add("META-INF/*")
    }

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.carebridge"
        minSdk = 24
        targetSdk = 34
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.constraintlayout)

    //implementation("com.google.firebase:firebase-firestore:19.0.2")
    implementation("com.google.cloud:google-cloud-dialogflow:2.2.0")
    implementation("io.grpc:grpc-okhttp:1.30.0")

    implementation("com.google.guava:guava:27.0.1-android")
    implementation("com.github.stfalcon-studio:Chatkit:0.4.1")
    implementation("com.google.android.flexbox:flexbox:3.0.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.mikhaellopez:circularprogressbar:3.1.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.squareup.picasso:picasso:2.8")
    implementation("com.getkeepsafe.taptargetview:taptargetview:1.13.2")
    implementation("com.android.volley:volley:1.2.1")
    implementation("com.google.code.gson:gson:2.8.8")
    implementation("ai.api:libai:1.6.12")
    implementation("ai.api:sdk:2.0.7@aar")

    //dagger-hilt
    implementation("com.google.dagger:hilt-android:2.50")
    implementation(libs.androidx.lifecycle.viewmodel.android)
    kapt("com.google.dagger:hilt-android-compiler:2.50")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")

    // Google Maps
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    modules {
        module("com.google.android:flexbox") {
            replacedBy("com.google.android.flexbox:flexbox")
        }
    }
}