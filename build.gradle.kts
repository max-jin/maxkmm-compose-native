// Remove "DSL_SCOPE_VIOLATION" once KTIJ-19369 is fixed
@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.comAndroidApplication).apply(false)
    alias(libs.plugins.comAndroidLibrary).apply(false)
    alias(libs.plugins.orgJetbrainsKotlinAndroid).apply(false)
    alias(libs.plugins.orgJetbrainsKotlinMultiplatform).apply(false)
    alias(libs.plugins.orgJetbrainsKotlinJvm).apply(false)
    alias(libs.plugins.orgJetbrainsCompose).apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
