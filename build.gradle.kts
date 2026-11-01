plugins {
    kotlin("jvm") version "1.9.22"
    id("com.android.library") version "8.2.2"
    id("org.mozilla.rust-android-gradle.rust-android") version "0.9.3"
}

group = "io.opacc"
version = "1.0000"

android {
    namespace = "io.opacc.patcher"
    compileSdk = 34
    ndkVersion = "26.1.10909125"

    defaultConfig {
        minSdk = 26
    }

    // Compile for all primary Android architectures.
    ndk {
        abiFilters.addAll(listOf("arm64-v8a", "armeabi-v7a", "x86", "x86_64"))
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
}

cargo {
    module = "src/main/rust"
    libname = "opacc_native"
    targets = listOf("arm64-v8a", "armeabi-v7a", "x86", "x86_64")
    profile = "release"
}

dependencies {
    // Core DEX manipulation libraries.
    implementation("org.smali:smali:3.0.5")
    implementation("org.smali:baksmali:3.0.5")
    implementation("com.android.tools.build:apksig:8.2.2")
}
