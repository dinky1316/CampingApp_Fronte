plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")

}

android {
    namespace = "com.camp.campingapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.camp.campingapp"
        minSdk = 24
        targetSdk = 33
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
//    자바1.8버전
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    viewBinding{
        enable = true
    }
    buildFeatures {
        viewBinding = true
    }



}

dependencies {

    //파이어베이스 파트
    implementation(platform("com.google.firebase:firebase-bom:32.2.2"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-messaging-ktx:23.2.1")
    implementation("com.google.firebase:firebase-database-ktx:20.2.2")
    implementation("com.google.firebase:firebase-firestore-ktx:24.7.0")
    implementation("com.google.firebase:firebase-storage-ktx:20.2.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    implementation ("com.google.firebase:firebase-bom:29.0.0")
    implementation ("com.google.android.gms:play-services-auth:19.2.0")
    implementation ("androidx.multidex:multidex:2.0.1")
//    뷰페이저
    implementation("androidx.viewpager2:viewpager2:1.0.0")


    // gps
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.naver.maps:map-sdk:3.17.0")

    // 카드뷰
    implementation ("androidx.cardview:cardview:1.0.0")

    implementation ("com.google.android.libraries.places:places:3.2.0")
    implementation ("com.android.volley:volley:1.2.1")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.google.code.gson:gson:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.github.bumptech.glide:glide:4.12.0")

    implementation ("androidx.core:core-ktx:1.8.0")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.5.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.annotation:annotation:1.3.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    implementation ("com.google.android.gms:play-services-maps:18.0.2")
    implementation ("androidx.legacy:legacy-support-v4:1.0.0")
    implementation ("androidx.recyclerview:recyclerview:1.3.0")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
//    구글맵스
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("androidx.annotation:annotation:1.3.0")


    implementation ("com.firebaseui:firebase-ui-storage:8.0.0")
//    깃허브 컴파일러
    implementation ("com.github.bumptech.glide:compiler:4.12.0")
//    페이스북 sdk
    implementation ("com.facebook.android:facebook-android-sdk:[4,5)")
    implementation ("com.facebook.android:facebook-login:latest.release")
    implementation ("com.sothree.slidinguppanel:library:3.4.0")

//    이미지뷰 둥글
    implementation ("de.hdodenhof:circleimageview:3.1.0")



}