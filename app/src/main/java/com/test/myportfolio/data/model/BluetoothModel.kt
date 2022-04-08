package com.test.myportfolio.data.model

import com.test.myportfolio.base.Item

data class BluetoothModel(
    override var id: Int = 0,
    override var viewType: Int = 0,
    val uuid: String = "",
    val mac: String = "",
    var time: String = "",
    var heart: String = "",
    var step: String = "",
    var wear: String = "",
    var rssi: String = "",
    val name: String = "",
    var latitude: String = "",
    var longitude: String = "",
    var msg: String = ""
) : Item()
