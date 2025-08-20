package org.example.app

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue

class SmokeTest {
    @Test
    fun testEnvironmentIsConfigured() {
        // Minimal assertion to ensure at least one test is discovered and executed.
        assertTrue(true, "CI smoke test should always pass")
    }
}
