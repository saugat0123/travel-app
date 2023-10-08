package com.example.suitcase.response

import com.example.rblibrary.entity.User

data class LoginResponse(
        val success: Boolean? = null,
        val token: String? = null,
        val data: User? = null
)

