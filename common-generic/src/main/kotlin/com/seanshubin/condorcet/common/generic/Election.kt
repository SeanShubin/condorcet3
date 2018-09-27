package com.seanshubin.condorcet.common.generic

class Election(val owner:String, val name:String){
    val ballots = mutableMapOf<String, Ballot>()
    var voters = listOf<String>()
    var candidates = listOf<String>()
}
