dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
    }
}
rootProject.name = "learn_compose"
include(":app")

apply { from("flutter_settings.gradle") }
include(":fluttermodule")
include(":openglmodule")
