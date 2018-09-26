package com.seanshubin.condorcet.js.frontend

import kotlin.test.Test
import kotlin.test.assertEquals

class JsFrontendGreeterTest {
    @Test
    fun greetTest() {
        val greeter = JsFrontendGreeter()
        assertEquals("Hello, world!", greeter.greet("world"))
    }
}
