import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

dependencies {
    implementation(projects.shared)

    implementation(compose.desktop.currentOs)
    implementation(libs.kotlinx.coroutinesSwing)

    implementation(libs.compose.uiToolingPreview)
}

compose.desktop {
    application {
        mainClass = "com.example.tpkmp.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "WeatherApp" //Nom de l'application
            packageVersion = "1.0.0"
            description = "Une application météo"
            vendor = "MonEntreprise"

            windows {
                shortcut = true
                menu = true
                // Icône pour l'application et l'installateur
                //iconFile.set(project.file("src/commonMain/composeResources/drawable/my_icone.ico"))
            }
        }
    }
}