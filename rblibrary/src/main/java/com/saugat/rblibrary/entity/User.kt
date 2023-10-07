package com.saugat.rblibrary.entity

import android.os.Parcel
import android.os.Parcelable



data class User (
        var _id: String? = null,
        var firstName : String? = null,
        var lastName : String? = null,
        var password : String? = null,
        var address : String? = null,
        var photo: String? = null,
        var phone : String? = null,
        var email : String? = null,
        )