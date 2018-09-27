package com.seanshubin.condorcet.common.generic

interface Api {
    fun register(userEmail:String, password:String):Result
    fun authenticate(userEmail:String, password:String):Result
    fun createElection(userEmail:String, electionName:String):Result
    fun castBallot(userEmail:String, electionName:String, rankings:List<Ranking>):Result
    fun abstainBallot(userEmail:String, electionName:String):Result
    fun resetBallot(userEmail:String, electionName:String):Result
    fun setVoters(userEmail:String, electionName:String, voters:List<String>):Result
    fun setCandidates(userEmail:String, electionName:String, candidates:List<String>):Result
    fun openElection(userEmail:String, electionName:String):Result
    fun closeElection(userEmail:String, electionName:String):Result
    fun removeElection(userEmail:String, electionName:String):Result
    fun reopenElection(userEmail:String, electionName:String):Result
    fun resetElection(userEmail:String, electionName:String):Result
}
