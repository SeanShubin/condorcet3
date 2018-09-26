package com.seanshubin.condorcet.jvm.backend

import kotlin.test.Test
import kotlin.test.assertEquals

class JvmBackendGreeterTest {
    @Test
    fun greetTest() {
        val greeter = JvmBackendGreeter()
        assertEquals("Hello, world!", greeter.greet("world"))
    }
}
