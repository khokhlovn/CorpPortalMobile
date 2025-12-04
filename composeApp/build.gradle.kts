import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.application)
}

kotlin {
    androidTarget()

    jvm()

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            export(projects.feature.root.rootComponent)
            export(libs.decompose)
            export(libs.essenty.lifecycle)
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.root.rootComponent)
            implementation(projects.data.network)
            implementation(projects.common.commonUi)
            implementation(projects.common.commonData)

            implementation(compose.runtime)

            implementation(libs.decompose)
            implementation(libs.decompose.compose)
            implementation(libs.kotlin.inject.runtime)

            implementation(libs.napier)

            implementation(libs.ktor.client.core)

        }

        androidMain.dependencies {
            implementation(projects.data.network)
            implementation(libs.androidx.activityCompose)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.appmetrica)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }

        iosMain.dependencies {
            api(projects.feature.root.rootComponent)
            api(libs.decompose)
            api(libs.essenty.lifecycle)
        }
    }
}

android {
    namespace = "ru.kama_diesel.corp_portal_mobile"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()

        applicationId = "ru.kama_diesel.corp_portal_mobile.androidApp"
        versionCode = libs.versions.app.versionCode.get().toInt()
        versionName = libs.versions.app.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        //enables a Compose tooling support in the AndroidStudio
        compose = true
    }
    lint {
        warningsAsErrors = true
        htmlReport = true
    }

    val apkName = "CorpPortalMobile-${libs.versions.app.versionName.get()}.${libs.versions.app.versionCode.get()}.apk"

    buildOutputs.all {
        val variantOutputImpl = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl
        variantOutputImpl.outputFileName = apkName
    }

    buildTypes {
        release {
            // Enables code-related app optimization.
            isMinifyEnabled = true

            // Enables resource shrinking.
            isShrinkResources = true

            proguardFiles(
                // Default file with automatically generated optimization rules.
                getDefaultProguardFile("proguard-android-optimize.txt"),
            )
        }
    }
}

compose.desktop {
    application {
        mainClass = "ru.kama_diesel.corp_portal_mobile.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "ru.kama_diesel.corp_portal_mobile.desktopApp"
            packageVersion = libs.versions.app.versionName.get()

            buildTypes.release.proguard {
                isEnabled.set(false)
            }
        }
    }
}

dependencies {
    add("kspAndroid", libs.kotlin.inject.compiler)
    add("kspJvm", libs.kotlin.inject.compiler)
    add("kspIosSimulatorArm64", libs.kotlin.inject.compiler)
    add("kspIosArm64", libs.kotlin.inject.compiler)
    coreLibraryDesugaring(libs.desugar)
}
