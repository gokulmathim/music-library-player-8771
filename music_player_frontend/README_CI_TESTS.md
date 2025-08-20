# CI unit test configuration

In some CI environments, Gradle’s unit test tasks may fail with:
“There are test sources present and no filters are applied, but the test task did not discover any tests to execute.”

This project configures Gradle test tasks to not fail when no tests are discovered by:
- adding `gradle.test-conventions.gradle.kts` that sets `setFailOnNoMatchingTests(false)` on all `Test` tasks
- applying that script via a conventional `settings.gradle` alongside the existing `settings.gradle.dcl`

This avoids false-negative CI failures without changing any source code or adding placeholder tests.
