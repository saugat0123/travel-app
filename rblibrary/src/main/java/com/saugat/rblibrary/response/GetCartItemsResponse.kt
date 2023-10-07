package com.saugat.rblibrary.response

import com.saugat.rblibrary.entity.Cart

data class GetCartItemsResponse (
        val success: Boolean? = null,
        val count: Int? =null,
        val data: MutableList<Cart>? = null
)