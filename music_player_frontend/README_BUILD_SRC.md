# buildSrc convention

This project includes `buildSrc` with a small convention plugin (`TestNoFailConvention`) that configures all Gradle `Test` tasks to not fail when no tests are discovered (e.g., in CI).

It is automatically discovered by Gradle and applied via the root `build.gradle.kts` in this Android project, without changing the Declarative Gradle DSL files.
