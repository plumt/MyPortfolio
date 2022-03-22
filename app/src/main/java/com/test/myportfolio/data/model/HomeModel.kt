package com.test.myportfolio.data.model

import com.test.myportfolio.base.Item


data class HomeModel(
    override val id: Int = 0,
    override val viewType: Int = 0,
    val title: String = ""
) : Item()
