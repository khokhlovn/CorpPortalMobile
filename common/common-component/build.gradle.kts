plugins {
    alias(libs.plugins.multiplatform)
}

kotlin {
    jvm()

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.common.commonUi)

            implementation(libs.decompose)
        }
    }
}