package com.seanshubin.condorcet.common.backend

import kotlin.test.*

class RankingTest {
    @Test
    fun rankingToRow() {
        // given
        val rank = 3
        val candidate = "some candidate"
        val ranking = Ranking(rank, candidate)
        val expected = listOf(rank, candidate)

        // when
        val actual = ranking.toRow()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun rankingValidateCandidateOnListDoesNotThrowException() {
        // given
        val rank = 2
        val candidate = "Bob"
        val ranking = Ranking(rank, candidate)
        val candidates = listOf("Alice", "Bob", "Carol")

        // when
        ranking.validate(candidates)
    }

    @Test
    fun rankingValidateCandidateNotOnListThrowsException() {
        // given
        val rank = 1
        val candidate = "Dave"
        val ranking = Ranking(rank, candidate)
        val candidates = listOf("Alice", "Bob", "Carol")

        // when
        val theException = assertFailsWith<Throwable>("foo") { ranking.validate(candidates) }

        // then
        assertEquals("invalid candidate Dave, valid candidates are Alice, Bob, Carol", theException.message)
    }
}
