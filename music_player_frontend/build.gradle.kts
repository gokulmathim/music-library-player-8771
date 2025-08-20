plugins {
    // no root plugins
}

subprojects {
    // Apply the convention plugin from buildSrc by its ID
    apply(plugin = "conventions.test-no-fail")
}
