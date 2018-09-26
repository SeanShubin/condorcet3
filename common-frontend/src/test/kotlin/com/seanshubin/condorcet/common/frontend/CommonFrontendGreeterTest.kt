package com.seanshubin.condorcet.common.frontend

import kotlin.test.Test
import kotlin.test.assertEquals

class CommonFrontendGreeterTest {
    @Test
    fun greetTest() {
        val greeter = CommonFrontendGreeter()
        assertEquals("Hello, world!", greeter.greet("world"))
    }
}
