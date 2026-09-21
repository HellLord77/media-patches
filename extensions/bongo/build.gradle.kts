dependencies {
    compileOnly(libs.gson)
    compileOnly(libs.okhttp)
    compileOnly(libs.retrofit)

    compileOnly(project(":extensions:bongo:stub"))
    compileOnly(project(":extensions:shared:library"))

    implementation(libs.david.webb)
}
