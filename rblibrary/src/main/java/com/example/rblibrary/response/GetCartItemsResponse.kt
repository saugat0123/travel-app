package com.example.rblibrary.response

import com.example.rblibrary.entity.Cart

data class GetCartItemsResponse (
        val success: Boolean? = null,
        val count: Int? =null,
        val data: MutableList<Cart>? = null
)