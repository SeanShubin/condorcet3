package com.seanshubin.condorcet.common.backend

import kotlin.test.Test
import kotlin.test.assertEquals

class CommonBackendGreeterTest {
    @Test
    fun greetTest() {
        val greeter = CommonBackendGreeter()
        assertEquals("Hello, world!", greeter.greet("world"))
    }
}
