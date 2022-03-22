package com.test.myportfolio.data.model

object PapagoModel {
    data class RS(
        val message: Message
    ){
        data class Message(
            val result: Result
        ){
            data class Result(
                val srcLangType: String = "",
                val tarLangType: String = "",
                val translatedText: String = ""
            )
        }
    }
}