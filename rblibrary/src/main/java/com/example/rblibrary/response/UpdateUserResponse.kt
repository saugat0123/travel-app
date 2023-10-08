package com.example.rblibrary.response

import com.example.rblibrary.entity.User

data class UpdateUserResponse(
        val success: Boolean? = null,
        val data: User? = null
)

