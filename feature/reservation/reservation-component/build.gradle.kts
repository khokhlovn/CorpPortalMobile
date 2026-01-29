import com.google.devtools.ksp.gradle.KspAATask

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    jvm()

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
        }
        commonMain.dependencies {
            implementation(projects.feature.reservation.reservationUi)
            implementation(projects.feature.reservation.reservationDomain)
            implementation(projects.feature.reservation.reservationData)

            implementation(projects.common.commonComponent)
            implementation(projects.common.commonDomain)
            implementation(projects.common.commonUi)
            implementation(projects.common.commonData)

            implementation(libs.decompose)
            implementation(libs.decompose.compose)
            implementation(libs.kotlin.inject.runtime)
            implementation(libs.kotlinx.datetime)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

dependencies {
    kspCommonMainMetadata(libs.kotlin.inject.compiler)
}

tasks.withType<KspAATask>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}
