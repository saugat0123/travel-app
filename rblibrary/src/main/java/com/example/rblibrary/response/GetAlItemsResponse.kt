package com.example.rblibrary.response

import com.example.rblibrary.entity.Item

data class GetAlItemsResponse (
        val success: Boolean? = null,
        val count: Int? =null,
        val data: MutableList<Item>? = null
)