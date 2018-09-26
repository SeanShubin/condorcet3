package com.seanshubin.condorcet.jvm.backend

import com.seanshubin.condorcet.common.backend.Condorcet
import org.junit.Test
import kotlin.test.assertTrue

class CondorcetTest {
    val classLoader = this.javaClass.classLoader
    @Test
    fun testDataSamples() {
        runTest("01-contrast-first-past-the-post")
        runTest("02-reduce-tactical-voting")
        runTest("03-contrast-instant-runoff")
        runTest("04-resolve-cycle-using-schulze-method")
        runTest("05-schulze-example-from-wikipedia")
        runTest("06-vote-against")
        runTest("07-ballot-can-have-ties")
        runTest("08-result-can-have-ties")
        runTest("09-random-data")
    }

    private fun runTest(name: String) {
        val inputLines = resourceNameToLines("test-data/$name/input.txt")
        val actualLines = Condorcet.processLines(inputLines)
        val expectedLines = resourceNameToLines("test-data/$name/expected.txt")
        assertLinesEqual(name, actualLines, expectedLines)
    }

    private fun assertLinesEqual(name: String, actualLines: List<String>, expectedLines: List<String>) {
        val result = LinesCompare.diff(actualLines, expectedLines)
        if (!result.isSame) {
            println("difference in test $name")
            actualLines.forEach { println(it) }
        }
        assertTrue(result.isSame, result.messageLines.joinToString("\n"))
    }

    private fun resourceNameToLines(name: String): List<String> {
        val inputStream = classLoader.getResourceAsStream(name)
        if (inputStream == null) {
            throw RuntimeException("Unable to find resource named '$name'")
        } else {
            return IoUtil.inputStreamToLines(inputStream)
        }
    }
}