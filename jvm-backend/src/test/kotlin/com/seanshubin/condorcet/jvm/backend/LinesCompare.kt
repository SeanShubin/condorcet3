package com.seanshubin.condorcet.jvm.backend

object LinesCompare {
    fun <T> diff(a: List<T>, b: List<T>): DifferenceResult {
        var index = 0
        var isSame = true
        var header = "identical"
        val messageLines = mutableListOf<String>()

        while (isSame && (index < a.size || index < b.size)) {
            val lineNumber = index + 1
            when {
                index < a.size && index < b.size -> {
                    val lineA = a[index]
                    val lineB = b[index]
                    if (a[index] == b[index]) {
                        messageLines.add("$lineNumber same        = $lineA")
                    } else {
                        isSame = false
                        header = "different at line $lineNumber"
                        messageLines.add("$lineNumber different-a = $lineA")
                        messageLines.add("$lineNumber different-b = $lineB")
                        messageLines.add("remaining elements skipped")
                    }
                }
                index < a.size -> {
                    val lineA = a[index]
                    isSame = false
                    header = "different at line $lineNumber"
                    messageLines.add("$lineNumber different-a = $lineA")
                    messageLines.add("$lineNumber different-b = <missing>")
                    messageLines.add("remaining elements skipped")

                }
                index < b.size -> {
                    val lineB = b[index]
                    isSame = false
                    header = "different at line $lineNumber"
                    messageLines.add("$lineNumber different-a = <missing>")
                    messageLines.add("$lineNumber different-b = $lineB")
                    messageLines.add("remaining elements skipped")
                }

            }
            index++
        }
        messageLines.add(0, header)
        return DifferenceResult(isSame = isSame, messageLines = messageLines)
    }
}
