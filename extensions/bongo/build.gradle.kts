dependencies {
    compileOnly(libs.gson)
    compileOnly(libs.okhttp)
    compileOnly(libs.retrofit)
    compileOnly(project(":extensions:bongo:stub"))

    implementation(libs.david.webb)
}
