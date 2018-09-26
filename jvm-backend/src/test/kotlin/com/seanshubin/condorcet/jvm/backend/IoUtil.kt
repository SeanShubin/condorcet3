package com.seanshubin.condorcet.jvm.backend

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

object IoUtil {
    fun inputStreamToLines(inputStream: InputStream): List<String> {
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.use {
            return bufferedReaderToLines(reader)
        }
    }

    private fun bufferedReaderToLines(reader: BufferedReader): List<String> {
        val mutableList = mutableListOf<String>()
        var line = reader.readLine()
        while (line != null) {
            mutableList.add(line)
            line = reader.readLine()
        }
        return mutableList
    }
}
