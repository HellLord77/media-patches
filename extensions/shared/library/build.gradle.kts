dependencies {
    api(libs.morphe.extensions.library)
}

plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "app.morphe.extension.shared"
    compileSdk = 36

    defaultConfig {
        minSdk = 26
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}