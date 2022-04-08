package com.test.myportfolio.data.model

import com.test.myportfolio.base.Item

class MovieModel {
    data class RS(
        val items: ArrayList<List>?
    ){
        data class List(
            override var id: Int = 0,
            override var viewType: Int = 0,
            val title: String = "",
            val link: String = "",
            val image: String = "",
            val pubDate: String = "",
            val director: String = "",
            val actor: String = "",
            val userRating: Float = 0f
        ) : Item()
    }
}