package com.saugat.rblibrary.entity

import android.os.Parcel
import android.os.Parcelable

data class Cart(
        var _id: String? = null,
        val itemName: String? = null,
        val photo: String? = null,
        val quantity: Int? = null,
        val itemPrice: String? = null
)