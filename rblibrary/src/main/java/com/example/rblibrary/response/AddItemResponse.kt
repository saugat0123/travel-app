package com.example.rblibrary.response

import com.example.rblibrary.entity.Item

data class AddItemResponse (
    val success: Boolean? = null,
    val data: Item? = null
        )