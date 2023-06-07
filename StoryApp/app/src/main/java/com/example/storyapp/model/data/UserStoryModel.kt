package com.example.storyapp.model.data

import android.os.Parcel
import android.os.Parcelable

data class UserStoryModel(
    val name: String?,
    val photo: String?,
    val deskripsi: String?,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(photo)
        parcel.writeString(deskripsi)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserStoryModel> {
        override fun createFromParcel(parcel: Parcel): UserStoryModel {
            return UserStoryModel(parcel)
        }

        override fun newArray(size: Int): Array<UserStoryModel?> {
            return arrayOfNulls(size)
        }
    }
}