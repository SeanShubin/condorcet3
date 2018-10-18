package com.seanshubin.condorcet.js.app

import com.seanshubin.condorcet.common.backend.Condorcet
import com.seanshubin.condorcet.js.frontend.Ranking
import com.seanshubin.condorcet.js.frontend.SampleData
import kotlinx.html.*
import kotlinx.html.dom.append
import kotlin.browser.document

val inputLines = SampleData.sampleInput

fun HtmlBlockTag.displayInputLines() {
    h2 {
        +"Input"
    }
    pre {
        +inputLines.joinToString("\n")
    }
}

fun HtmlBlockTag.displayOutputLines() {
    val outputLines = Condorcet.processLines(inputLines)
    h2 {
        +"Output"
    }
    pre {
        +outputLines.joinToString("\n")
    }
}

fun HtmlBlockTag.myFieldSet(name: String, block: () -> Unit) {
    fieldSet {
        legend { +name }
        block()
    }
}


fun HtmlBlockTag.spanLabel(text: String) {
    span("label") {
        +text
    }
}

fun HtmlBlockTag.divLabel(text: String) {
    div("label") {
        +text
    }
}

fun HtmlBlockTag.myInput(text: String) {
    div {
        spanLabel(text)
        input(classes = "entry")
    }
}

fun HtmlBlockTag.myPassword(text: String) {
    div {
        spanLabel(text)
        input(classes = "entry")
    }
}

fun HtmlBlockTag.myButton(text: String) {
    div {
        button {
            +text
        }
    }
}

fun HtmlBlockTag.renderRankingInput() {
    input(classes = "ranking-input")
}

fun HtmlBlockTag.renderRanking(ranking: Ranking) {
    div {
        renderRankingInput()
        +ranking.candidate
    }
}

fun HtmlBlockTag.login() {
    myFieldSet("Login") {
        myInput("EMail")
        myPassword("Password")
        myButton("Login")
        a(href = "") {
            +"Register"
        }
    }
}

fun HtmlBlockTag.register() {
    myFieldSet("Register") {
        myInput("EMail")
        myPassword("Password")
        myPassword("Confirm Password")
        myButton("Register")
        a(href = "") {
            +"Login"
        }
    }
}

fun HtmlBlockTag.elections(electionNames: List<String>) {
    myFieldSet("Elections") {
        ul {
            for (electionName in electionNames) {
                li {
                    a(href = electionName) {
                        +electionName
                    }
                }
            }
        }
        myButton("Create Election")
        myButton("Logout")
    }
}

fun HtmlBlockTag.createElection() {
    myFieldSet("Create Election") {
        myInput("Election Name")
        divLabel("Candidate Names")
        div {
            textArea {}
        }
        divLabel("Applicable Voters")
        div {
            textArea {}
        }
        myButton("Create Election")
        myButton("Logout")
    }
}

fun HtmlBlockTag.election(name: String) {
    myFieldSet(name) {
        myButton("Cast Ballot")
        myButton("Tally")
        myButton("Mark for Delete")
        myButton("Logout")
    }
}

fun HtmlBlockTag.ballot(name: String, rankings: List<Ranking>) {
    myFieldSet("Ballot for $name") {
        for (ranking in rankings) {
            renderRanking(ranking)
        }
        myButton("Apply Rankings")
        myButton("Tally")
        myButton("Logout")
    }
}

fun main(arguments: Array<String>) {
    document.body!!.append.div() {
        login()
        register()
        elections(SampleData.electionNames)
        election(SampleData.election1)
        ballot(SampleData.election1, SampleData.rankings)
        createElection()
        displayInputLines()
        displayOutputLines()
    }
}
