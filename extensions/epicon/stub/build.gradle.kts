dependencies {
    api(libs.gson)
    api(libs.retrofit)

    api(libs.okhttp) {
        version {
            strictly("4.12.0")
        }
    }
}

plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "app.morphe.extension"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
    }
}
