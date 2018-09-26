package com.seanshubin.condorcet.common.backend

import kotlin.test.Test
import kotlin.test.assertEquals

class StringTest {
    @Test
    fun splitString(){
        // given
        val target = """
            |abc
            |def
            |ghi
        """.trimMargin().trim()
        val splitPattern = arrayOf("\r\n", "\r", "\n")
        val expected = listOf("abc", "def", "ghi")

        // when
        val actual = target.split(*splitPattern)

        // then
        assertEquals(expected, actual)
    }
}
