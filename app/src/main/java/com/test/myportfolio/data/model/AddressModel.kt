package com.test.myportfolio.data.model

import com.test.myportfolio.base.Item
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

object AddressModel {
    @Xml(name="NewAddressListResponse")
    data class RS(
        @Element
        val cmmMsgHeader: CmmMsgHeader,
        @Element
        val newAddressListAreaCd: List<NewAddressListAreaCd>
    ){
        @Xml(name = "cmmMsgHeader")
        data class CmmMsgHeader(
            @PropertyElement
            val requestMsgId: String = "",
            @PropertyElement
            val responseMsgId: String = "",
            @PropertyElement
            val responseTime: String = "",
            @PropertyElement
            val successYN: String = "",
            @PropertyElement
            val returnCode: String = "",
            @PropertyElement
            val errMsg: String = "",
            @PropertyElement
            val totalCount: String = "",
            @PropertyElement
            val countPerPage: String = "",
            @PropertyElement
            val totalPage: String = "",
            @PropertyElement
            val currentPage: String = ""
        )


        @Xml(name = "newAddressListAreaCd")
        data class NewAddressListAreaCd(
            @PropertyElement
            val zipNo: String = "",
            @PropertyElement
            val lnmAdres: String = "",
            @PropertyElement
            val rnAdres: String = ""
        )
    }

    data class AddressModels(
       val items: ArrayList<List>
    ) {
        data class List(
        override val id: Int = 0,
        override val viewType: Int = 0,
        val zipNo: String = "",
        val lnmAdres: String = "",
        val rnAdres: String = ""
        ) : Item()
    }


}