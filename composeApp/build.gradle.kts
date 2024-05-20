import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.io.FileInputStream
import java.util.Properties


plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
}

room {
    schemaDirectory("$projectDir/schemas")
}

kotlin {

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }

    }
    /*mingwX64("native") {
            binaries { executable() }
        }*/
    /*macosX64("native") {
        binaries {
            executable()
        }
    }*/

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "composeApp"
            isStatic = true
            // Required when using NativeSQLiteDriver
            linkerOpts.add("-lsqlite3")

        }
    }
    task("testClasses")

    // val ktorVersion = "2.3.9"
    // val dateTimeVersion="0.4.1"

    sourceSets {
        val desktopMain by getting
        val voyagerVersion = "1.0.0"
        androidMain.dependencies {
            implementation(libs.ktor.client.android)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.activity.ktx)
            implementation(libs.androidx.room.paging)
            implementation("cafe.adriel.voyager:voyager-livedata:$voyagerVersion")

        }
        commonMain.dependencies {
            implementation("com.ionspin.kotlin:bignum:0.3.9")
            //implementation("androidx.lifecycle:lifecycle-livedata-core:2.8.0-beta01")
           implementation(libs.androidx.livedata)
            implementation(libs.androidx.lifecycle.livedata.core.ktx)
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.paging.common)
            implementation(libs.sqlite.bundled)
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0-beta01")
            implementation(libs.ktor.client.logging)
            implementation("org.jetbrains.kotlinx:kotlinx-io-core:0.3.3")
            implementation("com.russhwolf:multiplatform-settings-no-arg:1.1.1")
            //implementation(libs.coil)
            implementation("io.coil-kt.coil3:coil-compose:3.0.0-alpha06")
            implementation("io.coil-kt.coil3:coil-network-ktor:3.0.0-alpha06")
            //implementation("io.coil-kt.coil3:coil:3.0.0-alpha01")
            //implementation("io.coil-kt.coil3:coil-network-ktor:3.0.0-alpha01")
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.datetime)
            // implementation(libs.accompanist.systemuicontroller)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")

            // Screen Model
            implementation("cafe.adriel.voyager:voyager-screenmodel:$voyagerVersion")

            // BottomSheetNavigator
            implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:$voyagerVersion")

            // TabNavigator
            implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")

            // Transitions
            implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")

        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        iosX64Main.dependencies {
            implementation(libs.ktor.client.darwin)

        }
        iosArm64Main.dependencies {
            implementation(libs.ktor.client.darwin)

        }
        iosSimulatorArm64Main.dependencies {
            implementation(libs.ktor.client.darwin)

        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation("io.ktor:ktor-client-cio-jvm:2.3.2")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing")

        }
    }
}
val keystoreProperties = Properties()
var keystorePropertiesFile = rootProject.file("/Users/fernandosini/Bitcoin/composeApp/src/androidMain/key.properties")
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}
println(keystoreProperties)

android {
    namespace = "com.fernandosini.bitcoin"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.fernandosini.bitcoin"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    signingConfigs {
        //lembrar que parei aqui pra configurar os apks de release
        create("release") {

            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
            //storeFile = file(keystoreProperties.getProperty("storeFile"))
            storeFile = file("/Users/fernandosini/Bitcoin/composeApp/mykeystore")
            storePassword = keystoreProperties.getProperty("storePassword")
        }

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
        }
        release {
            // TODO: Add your own signing config for the release build.
            signingConfig =
                signingConfigs.findByName("release") ?: signingConfigs.getByName("debug")
        }
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
        add("kspAndroid", libs.androidx.room.compiler)
        add("kspIosSimulatorArm64", libs.androidx.room.compiler)
        add("kspIosX64", libs.androidx.room.compiler)
        add("kspIosArm64", libs.androidx.room.compiler)
        //ksp(libs.androidx.room.compiler)
    }
}



compose.desktop {

    application {

        mainClass = "MainKt"


        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            //packageName = "com.fernandosini.bitcoin"
            packageName = "CryptoApp"
            packageVersion = "1.0.0"
            jvmArgs += listOf("-Dapple.awt.application.name=CryptoApp")
            macOS {
                bundleID = "com.fernandosini.bitcoin"
                packageName = "CryptoApp"
                dockName = "CryptoApp"

            }
            windows {
                packageName = "com.fernandosini.bitcoin"
                packageVersion = "1.0.0"
            }
        }
    }
}
