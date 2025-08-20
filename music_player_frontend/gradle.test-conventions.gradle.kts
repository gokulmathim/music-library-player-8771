import org.gradle.api.tasks.testing.Test

// Apply to all subprojects in this Android multi-project
gradle.allprojects {
    tasks.withType<Test>().configureEach {
        // Gradle 7.6+ property to avoid failing when no tests are discovered
        // Some Android Gradle Plugin versions use noMatching rather than discovered.
        // Set both defensively.
        systemProperty("org.gradle.junit.test.discovery.failOnNoDiscoveredTests", "false")
        // For older Gradle/AGP behavior:
        this.ignoreFailures = false
        // Kotlin DSL equivalent to classic failOnNoMatchingTests = false
        this.setFailOnNoMatchingTests(false)
    }
}
