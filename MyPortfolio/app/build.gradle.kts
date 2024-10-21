plugins {
    id("com.android.application")
}

android {
    namespace = "com.aspprothes.myportfolio"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aspprothes.myportfolio"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "prothes 2.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation("com.airbnb.android:lottie:3.4.0")
    implementation("com.makeramen:roundedimageview:2.3.0")
    implementation("com.github.Mursaat:AnimatedGradientTextView:v0.0.6")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("com.github.LionZXY.T-Rex-Android:trex-library:1.0.0")
}