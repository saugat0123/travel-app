package com.saugat.rblibrary.response

import com.saugat.rblibrary.entity.User

data class GetMeResponse (
        val success: Boolean? = null,
        val data: MutableList<User>? = null
)