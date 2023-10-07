package com.saugat.rblibrary.response

import com.saugat.rblibrary.entity.User

data class GetUserProfileResponse (
        val success: Boolean? = null,
        val data: User? = null
)