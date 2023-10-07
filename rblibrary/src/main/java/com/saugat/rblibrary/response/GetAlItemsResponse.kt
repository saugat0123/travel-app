package com.saugat.rblibrary.response

import com.saugat.rblibrary.entity.Item

data class GetAlItemsResponse (
        val success: Boolean? = null,
        val count: Int? =null,
        val data: MutableList<Item>? = null
)