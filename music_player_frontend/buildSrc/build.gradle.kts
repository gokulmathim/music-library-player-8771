plugins {
    `java-gradle-plugin`
}

repositories {
    google()
    gradlePluginPortal()
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("testNoFailConvention") {
            id = "conventions.test-no-fail"
            implementationClass = "TestNoFailConvention"
        }
    }
}
