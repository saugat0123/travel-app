package com.example.rblibrary.response

import com.example.rblibrary.entity.User

data class GetUserProfileResponse (
        val success: Boolean? = null,
        val data: User? = null
)