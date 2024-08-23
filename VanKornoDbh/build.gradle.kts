plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.vankorno.vankornodbh"
    compileSdk = 35
    
    defaultConfig {
        minSdk = 21
        
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
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}

val buildDirectory = layout.buildDirectory.get()

// Add the following block to ensure the library is published correctly
publishing {
    publications {
        create<MavenPublication>("release") {
            afterEvaluate {
                from(components["release"]) // This part might still not work

                // Alternatively, specify the AAR directly
                artifact("$buildDirectory/outputs/aar/${project.name}-release.aar")
            }

            groupId = "com.vankorno"
            artifactId = "vankornodbh"
            version = "0.0.14"
        }
    }
}
