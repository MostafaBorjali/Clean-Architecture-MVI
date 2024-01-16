package com.borjali.domain.model.user

import android.os.Parcel
import android.os.Parcelable

data class UserDto(
    val id: String?,
    val email: String?,
    val role: String?,
    val status: String?,
    val createdAt: String?,
    val updatedAt: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        id = parcel.readString(),
        email = parcel.readString(),
        role = parcel.readString(),
        status = parcel.readString(),
        createdAt = parcel.readString(),
        updatedAt = parcel.readString(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(email)
        parcel.writeString(role)
        parcel.writeString(status)
        parcel.writeString(createdAt)
        parcel.writeString(updatedAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserDto> {
        override fun createFromParcel(parcel: Parcel): UserDto {
            return UserDto(parcel)
        }

        override fun newArray(size: Int): Array<UserDto?> {
            return arrayOfNulls(size)
        }
    }
}
