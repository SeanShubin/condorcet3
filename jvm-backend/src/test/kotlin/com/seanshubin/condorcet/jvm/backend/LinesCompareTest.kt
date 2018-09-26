package com.seanshubin.condorcet.jvm.backend

import org.junit.Test
import kotlin.test.assertEquals

class LinesCompareTest {
    @Test
    fun same() {
        val a = listOf(1, 2, 3)
        val b = listOf(1, 2, 3)
        val actual = LinesCompare.diff(a, b)
        val expectedMessage =
                """identical
                  |1 same        = 1
                  |2 same        = 2
                  |3 same        = 3""".trimMargin().split(Regex("""\r\n|\r|\n"""))
        assertEquals(true, actual.isSame)
        assertEquals(expectedMessage, actual.messageLines)
    }

    @Test
    fun difference() {
        val expectedMessage =
                """different at line 2
                  |1 same        = 1
                  |2 different-a = 2
                  |2 different-b = 4
                  |remaining elements skipped""".trimMargin().split(Regex("""\r\n|\r|\n"""))
        val a = listOf(1, 2, 3)
        val b = listOf(1, 4, 3)
        val actual = LinesCompare.diff(a, b)
        assertEquals(false, actual.isSame)
        assertEquals(expectedMessage, actual.messageLines)
    }

    @Test
    fun bShorter() {
        val a = listOf(1, 2, 3)
        val b = listOf(1, 2)
        val expected =
                """different at line 3
                  |1 same        = 1
                  |2 same        = 2
                  |3 different-a = 3
                  |3 different-b = <missing>
                  |remaining elements skipped""".trimMargin().split(Regex("""\r\n|\r|\n"""))
        val actual = LinesCompare.diff(a, b)
        assertEquals(false, actual.isSame)
        assertEquals(expected, actual.messageLines)
    }

    @Test
    fun aShorter() {
        val a = listOf(1, 2, 3)
        val b = listOf(1, 2, 3, 4)
        val expected =
                """different at line 4
                  |1 same        = 1
                  |2 same        = 2
                  |3 same        = 3
                  |4 different-a = <missing>
                  |4 different-b = 4
                  |remaining elements skipped""".trimMargin().split(Regex("""\r\n|\r|\n"""))
        val actual = LinesCompare.diff(a, b)
        assertEquals(false, actual.isSame)
        assertEquals(expected, actual.messageLines)
    }
}
