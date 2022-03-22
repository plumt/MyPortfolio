package com.test.myportfolio.data.model

import com.test.myportfolio.base.Item

object MovieModel {
    data class RS(
        val items: ArrayList<List>?
    ) {
        data class List(
            override val id: Int = 0,
            override val viewType: Int = 0,
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