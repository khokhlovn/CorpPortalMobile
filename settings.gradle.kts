enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "CorpPortalMobile"

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("android.*")
            }
        }
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("android.*")
            }
        }
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
        maven("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
    }
}
include(":composeApp")
include(":data:preferences")
include(":data:network")
include(":common:common-ui")
include(":common:common-component")
include(":common:common-domain")
include(":common:common-data")

include(":feature:root:root-domain")
include(":feature:root:root-ui")
include(":feature:root:root-component")

include("feature:main:main-domain")
include("feature:main:main-ui")
include("feature:main:main-component")

include(":feature:auth:auth-domain")
include(":feature:auth:auth-ui")
include(":feature:auth:auth-component")
include(":feature:auth:auth-data")

include("feature:articles:articles-component")
include("feature:articles:articles-data")
include("feature:articles:articles-domain")
include("feature:articles:articles-ui")

include("feature:shop:shop-component")
include("feature:shop:shop-data")
include("feature:shop:shop-domain")
include("feature:shop:shop-ui")
