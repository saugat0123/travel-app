package com.saugat.finalassignment.response

import com.saugat.rblibrary.entity.User

data class LoginResponse(
        val success: Boolean? = null,
        val token: String? = null,
        val data: User? = null
)

