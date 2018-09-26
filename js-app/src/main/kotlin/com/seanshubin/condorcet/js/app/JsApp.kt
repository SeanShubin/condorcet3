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
        p {
            +"successful login goes to electionList page"
        }
        p {
            +"unsuccessful login stays on login page"
        }
        p {
            +"link to register page"
        }
        myInput("EMail")
        myPassword("Password")
        myButton("Login")
    }
}

fun HtmlBlockTag.register() {
    myFieldSet("Register") {
        p {
            +"successful register goes to electionList page"
        }
        p {
            +"unsuccessful register stays on register page"
        }
        p {
            +"link to login page"
        }
        myInput("EMail")
        myPassword("Password")
        myPassword("Confirm Password")
        myButton("Register")
    }
}

fun HtmlBlockTag.elections(electionNames:List<String>) {
    myFieldSet("Elections") {
        p {
            +"add election goes to create election page"
        }
        p {
            +"link to each election goes to election page"
        }
        p {
            +"logout returns to login page"
        }
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
        p {
            +"create election goes to election page"
        }
        p {
            +"link to elections page"
        }
        myInput("Election Name")
        candidateEditor()
    }
}

fun HtmlBlockTag.election(name: String, rankings: List<Ranking>) {
    myFieldSet(name) {
        for (ranking in rankings) {
            renderRanking(ranking)
        }
        myButton("Apply Rankings")
    }
}

fun HtmlBlockTag.candidateEditor() {
    myFieldSet("Candidates") {
        divLabel("Candidate Names")
        div {
            textArea {}
        }
        myButton("Update")
    }
}

fun main(arguments: Array<String>) {
    document.body!!.append.div() {
        login()
        register()
        elections(SampleData.electionNames)
        election(SampleData.election1, SampleData.rankings)
        createElection()
        displayInputLines()
        displayOutputLines()
    }
}
