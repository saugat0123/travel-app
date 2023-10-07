package com.saugat.rblibrary.response

import com.saugat.rblibrary.entity.Item

data class AddItemResponse (
    val success: Boolean? = null,
    val data: Item? = null
        )