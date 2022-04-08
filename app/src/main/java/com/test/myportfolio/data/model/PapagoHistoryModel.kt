package com.test.myportfolio.data.model

import com.test.myportfolio.base.Item

data class PapagoHistoryModel(
    override var id: Int = 0,
    override var viewType: Int = 0,
    val key: String = "",
    val value: String = ""
) : Item()
