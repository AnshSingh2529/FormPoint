plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "aman.major.formpoint"
    compileSdk = 34

    defaultConfig {
        applicationId = "aman.major.formpoint"
        minSdk = 27
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    viewBinding{
        enable = true
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    /*For the Responsive dimensions */
    implementation ("com.intuit.ssp:ssp-android:1.1.0")
    implementation ("com.intuit.sdp:sdp-android:1.1.0")

    /*Elastic Layout*/
    implementation ("com.github.skydoves:elasticviews:2.1.0")

    /*Image Slider*/
    implementation ("com.github.dangiashish:Auto-Image-Slider:1.0.4")

    /*Lottie animation*/
    implementation ("com.airbnb.android:lottie:3.7.0")

    /*Retrofit*/
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")

    /*online images*/
   // implementation ("implementation 'com.squareup.picasso:picasso:2.8")
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    /*For Youtube video Play*/
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")

    /*Pin view*/
    implementation ("io.github.chaosleung:pinview:1.4.4")

}
