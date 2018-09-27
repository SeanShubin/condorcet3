package com.seanshubin.condorcet.common.generic

interface Result{
    companion object {
        object Success:Result
        data class Failure(val message:String):Result
    }
}
