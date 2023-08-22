// Remove "DSL_SCOPE_VIOLATION" once KTIJ-19369 is fixed
@file:Suppress("DSL_SCOPE_VIOLATION", "UNUSED_VARIABLE")

plugins {
    alias(libs.plugins.orgJetbrainsKotlinMultiplatform)
    alias(libs.plugins.comAndroidLibrary)
    alias(libs.plugins.orgJetbrainsKotlinSerialization)
    alias(libs.plugins.orgJetbrainsCompose)
    kotlin("native.cocoapods")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
        extraSpecAttributes["resources"] = "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }

    sourceSets {
        /**
         * Shared multiplatform dependencies here
         */
        val commonMain by getting {
            dependencies {
                // Compose
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(compose.ui)
                // implementation(compose.uiTooling) need iOS support
                implementation(compose.animation)
                // implementation(compose.preview) need iOS support
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                // Issue: https://github.com/ctripcorp/SQLlin/issues/29
                implementation(libs.orgJetbrainsKotlinxAtomicfu)

                // Kotlinx Coroutines
                implementation(libs.kotlinxCoroutinesCore)
                // Ktor (needs Kotlinx Serialization)
                implementation(libs.ktorClientCore)
                implementation(libs.ktorClientContentNegotiation)
                implementation(libs.ktorSerializationKotlinxJson)
                // SQLDelight
                implementation(libs.comSquareupSqldelightRuntime)
                // Kotlinx Date&time
                implementation(libs.orgJetbrainsKotlinxDatetime)
            }
        }

        /**
         * For platform Android main source
         * Notes: Both the SQLDelight and Ktor libraries need platform drivers in the iOS and Android source sets, as well.
         */
        val androidMain by getting {
            dependencies {
                // Androidx
                api(libs.androidxCoreKtx)
                api(libs.androidxAppcompat)
                api(libs.androidxActivityCompose)
                // SQLDelight
                implementation(libs.comSquareupSqldelightAndroidDriver)
                // Ktor
                implementation(libs.ktorClientAndroid)
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        /**
         * For platform iOS main source
         * Notes: Both the SQLDelight and Ktor libraries need platform drivers in the iOS and Android source sets, as well.
         */
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                // SQLDelight
                implementation(libs.comSquareupSqldelightNativeDriver)
                // Ktor
                implementation(libs.ktorClientDarwin)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = "com.maxjin.kmmcomposenative"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11)
    }
}