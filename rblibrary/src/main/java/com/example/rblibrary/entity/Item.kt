package com.example.rblibrary.entity

import android.os.Parcel
import android.os.Parcelable

data class Item(
    var _id: String? = null,
    val itemName: String? = null,
    val photo: String? = null,
    val itemType: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val itemPrice: Int? = null,
    val itemDes: String? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(itemName)
        parcel.writeString(photo)
        parcel.writeString(itemType)
        parcel.writeValue(latitude)
        parcel.writeValue(longitude)
        parcel.writeValue(itemPrice)
        parcel.writeString(itemDes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}
