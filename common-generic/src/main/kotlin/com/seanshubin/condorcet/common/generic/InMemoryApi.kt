package com.seanshubin.condorcet.common.generic

import com.seanshubin.condorcet.common.generic.Result.Companion.Failure
import com.seanshubin.condorcet.common.generic.Result.Companion.Success

class InMemoryApi : Api {
    private val users = mutableMapOf<String, User>()
    private val elections = mutableMapOf<String, Election>()
    override fun register(userEmail: String, password: String): Result {
        return if (users.contains(userEmail)) {
            Failure("User $userEmail is already registered")
        } else {
            users[userEmail] = User(userEmail, password)
            Success
        }
    }

    override fun authenticate(userEmail: String, password: String): Result {
        val user = users[userEmail]
        return when {
            user == null -> Failure("User $userEmail is not registered")
            user.password == password -> return Success
            else -> return Failure("Password does not match for user $userEmail")
        }
    }

    override fun createElection(userEmail: String, electionName: String): Result {
        return if (elections.contains(electionName)) {
            Failure("Election $electionName already exists")
        } else {
            elections[electionName] = Election(userEmail, electionName)
            Success
        }
    }

    override fun castBallot(userEmail: String, electionName: String, rankings: List<Ranking>): Result {
        return withElectionNamed(electionName) {
            it.ballots[userEmail] = Ballot(rankings)
            Success
        }
    }

    override fun abstainBallot(userEmail: String, electionName: String): Result {
        return withElectionNamed(electionName) {
            it.ballots[userEmail] = Ballot(emptyList())
            Success
        }
    }

    override fun resetBallot(userEmail: String, electionName: String): Result {
        return withElectionNamed(electionName) {
            it.ballots.remove(userEmail)
            Success
        }
    }

    override fun setVoters(userEmail: String, electionName: String, voters: List<String>): Result {
        return withElectionNamed(electionName) {
            it.voters = voters
            Success
        }
    }

    override fun setCandidates(userEmail: String, electionName: String, candidates: List<String>): Result {
        return withElectionNamed(electionName) {
            it.candidates = candidates
            Success
        }
    }

    override fun openElection(userEmail: String, electionName: String): Result {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun closeElection(userEmail: String, electionName: String): Result {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeElection(userEmail: String, electionName: String): Result {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reopenElection(userEmail: String, electionName: String): Result {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resetElection(userEmail: String, electionName: String): Result {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun withElectionNamed(electionName: String, f: (Election) -> Result): Result {
        val election = elections[electionName]
        return if (election == null) {
            Failure("Election named $electionName does not exist")
        } else {
            f(election)
        }
    }
}
