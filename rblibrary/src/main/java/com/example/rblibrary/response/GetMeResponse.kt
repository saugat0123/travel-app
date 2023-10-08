package com.example.rblibrary.response

import com.example.rblibrary.entity.User

data class GetMeResponse (
        val success: Boolean? = null,
        val data: MutableList<User>? = null
)