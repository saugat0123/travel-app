package com.example.rblibrary.response

import com.example.rblibrary.entity.Cart

data class AddCartResponse (
    val success: Boolean? = null,
    val data: Cart? = null
        )