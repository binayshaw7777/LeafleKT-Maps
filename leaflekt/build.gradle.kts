plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    `maven-publish`
}

val releaseVersion = rootProject.file("VERSION").readText().trim()
val pomGroupId = providers.gradleProperty("POM_GROUP_ID").get()
val pomArtifactId = providers.gradleProperty("POM_ARTIFACT_ID").get()
val pomName = providers.gradleProperty("POM_NAME").get()
val pomDescription = providers.gradleProperty("POM_DESCRIPTION").get()
val pomUrl = providers.gradleProperty("POM_URL").get()
val pomDeveloperId = providers.gradleProperty("POM_DEVELOPER_ID").get()
val pomDeveloperName = providers.gradleProperty("POM_DEVELOPER_NAME").get()
val pomScmUrl = providers.gradleProperty("POM_SCM_URL").get()
val pomScmConnection = providers.gradleProperty("POM_SCM_CONNECTION").get()
val pomScmDeveloperConnection = providers.gradleProperty("POM_SCM_DEV_CONNECTION").get()

android {
    namespace = "com.binayshaw7777.leaflekt.library"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

group = pomGroupId
version = releaseVersion

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.webkit)

    testImplementation(libs.junit)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = pomGroupId
            artifactId = pomArtifactId
            version = releaseVersion

            afterEvaluate {
                from(components["release"])
            }

            pom {
                name.set(pomName)
                description.set(pomDescription)
                url.set(pomUrl)

                developers {
                    developer {
                        id.set(pomDeveloperId)
                        name.set(pomDeveloperName)
                    }
                }

                scm {
                    url.set(pomScmUrl)
                    connection.set(pomScmConnection)
                    developerConnection.set(pomScmDeveloperConnection)
                }
            }
        }
    }
}
