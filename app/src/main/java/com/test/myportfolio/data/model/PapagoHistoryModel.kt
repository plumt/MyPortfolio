package com.test.myportfolio.data.model

import com.test.myportfolio.base.Item

data class PapagoHistoryModel(
    override val id: Int = 0,
    override val viewType: Int = 0,
    val key: String = "",
    val value: String = ""
) : Item()
