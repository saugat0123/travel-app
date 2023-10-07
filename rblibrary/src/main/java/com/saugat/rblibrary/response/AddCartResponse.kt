package com.saugat.rblibrary.response

import com.saugat.rblibrary.entity.Cart

data class AddCartResponse (
    val success: Boolean? = null,
    val data: Cart? = null
        )