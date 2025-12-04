plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.ksp)
}

kotlin {
    jvm()

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.common.commonDomain)
            implementation(libs.kotlin.inject.runtime)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.coroutines.core)
            api(projects.data.preferences)
            api(projects.data.network)
            api(libs.uuid)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}
