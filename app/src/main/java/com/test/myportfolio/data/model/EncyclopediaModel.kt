package com.test.myportfolio.data.model

import com.test.myportfolio.base.Item

object EncyclopediaModel {
    data class RS(
        val total: String = "",
        val start: String = "",
        val display: String = "",
        val items: List<Items>?
    ){
        data class Items(
            override var id: Int = 0,
            override var viewType: Int = 0,
            val title: String = "",
            val link: String = "",
            val thumbnail: String = "",
            val description: String = ""
        ): Item()
    }
}