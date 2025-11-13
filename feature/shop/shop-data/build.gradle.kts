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
            implementation(projects.feature.shop.shopDomain)
            implementation(projects.common.commonDomain)
            implementation(projects.common.commonData)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlin.inject.runtime)
            api(libs.ktor.client.core)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}
