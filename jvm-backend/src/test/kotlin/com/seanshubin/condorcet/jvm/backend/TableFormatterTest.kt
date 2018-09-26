package com.seanshubin.condorcet.jvm.backend

import com.seanshubin.condorcet.common.backend.TableFormatter
import org.junit.Test
import kotlin.test.assertEquals

class TableFormatterTest {
    @Test
    fun fancyTable() {
        val tableFormatter = TableFormatter(wantInterleave = true)
        val input = listOf(
                listOf("Alice", "Bob", "Carol"),
                listOf("Dave", "Eve", "Mallory"),
                listOf("Peggy", "Trent", "Wendy"))
        val expected = listOf(
                "╔═════╤═════╤═══════╗",
                "║Alice│Bob  │Carol  ║",
                "╟─────┼─────┼───────╢",
                "║Dave │Eve  │Mallory║",
                "╟─────┼─────┼───────╢",
                "║Peggy│Trent│Wendy  ║",
                "╚═════╧═════╧═══════╝"
        )
        val actual = tableFormatter.createTable(input)
        assertEquals(expected, actual)
    }

    @Test
    fun minimalTable() {
        val tableFormatter = TableFormatter(wantInterleave = false, rowLeft = "", rowCenter = " ", rowRight = "")
        val input = listOf(
                listOf("Alice", "Bob", "Carol"),
                listOf("Dave", "Eve", "Mallory"),
                listOf("Peggy", "Trent", "Wendy"))
        val expected = listOf(
                "Alice Bob   Carol  ",
                "Dave  Eve   Mallory",
                "Peggy Trent Wendy  "
        )
        val actual = tableFormatter.createTable(input)
        assertEquals(expected, actual)
    }
}
