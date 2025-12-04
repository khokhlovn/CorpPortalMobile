import com.google.devtools.ksp.gradle.KspTaskMetadata

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
        commonMain.dependencies {
            implementation(projects.feature.main.mainUi)
            implementation(projects.feature.main.mainDomain)

            implementation(projects.feature.articles.articlesComponent)
            implementation(projects.feature.articles.articlesDomain)

            implementation(projects.feature.shop.shopComponent)
            implementation(projects.feature.shop.shopDomain)

            implementation(projects.feature.phoneDirectory.phoneDirectoryComponent)
            implementation(projects.feature.phoneDirectory.phoneDirectoryDomain)

            implementation(projects.common.commonComponent)
            implementation(projects.common.commonDomain)
            implementation(projects.common.commonUi)
            implementation(projects.common.commonData)

            implementation(libs.decompose)
            implementation(libs.decompose.compose)
            implementation(libs.kotlin.inject.runtime)
            implementation(libs.ktor.client.core)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
        }
    }
}

dependencies {
    kspCommonMainMetadata(libs.kotlin.inject.compiler)
}

kotlin.sourceSets.commonMain { tasks.withType<KspTaskMetadata> { kotlin.srcDir(destinationDirectory) } }
