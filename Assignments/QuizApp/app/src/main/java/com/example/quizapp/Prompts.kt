package com.example.quizapp

class Prompts (val question: String, val answers: List<String>, val correctAnswer: String) {
    @JvmName("getQuestion1")
    public fun getQuestion(): String {
        return this.question
    }
}

var QuizQuestions = listOf<Prompts>(
    Prompts(
        "Who was the first Formula 1 World Champion?",
        listOf(
            "Nikki Lauda",
            "Graham Hill",
            "Qiueseppe Farina",
            "Juan Manuel Fangio"
        ),
        "Qiueseppe Farina"
    ),
    Prompts(
        "What was Twitter originally called?",
        listOf(
            "Twttr",
            "Snapster",
            "Birds",
            "Chirper"
        ),
        "Twttr"
    ),
    Prompts(
        "What planet has the strongest gravity?",
        listOf(
            "Jupiter",
            "Saturn",
            "Venus",
            "Uranus"
        ),
        "Jupiter"
    ),
    Prompts(
        "What character does the actor Rainn Wilson play in 'The Office'?",
        listOf(
            "Jim",
            "Dwight",
            "Ryan",
            "Stanley"
        ),
        "Dwight"
    ),
)
