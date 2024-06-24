// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.android.library") version "7.1.2" apply false
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.0" apply false
}