package com.example.myrecyclerview

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hero(
    val name: String,
    val description: String,
    val photo: Int,
    val date: String,
    val actor: String
): Parcelable {
    fun shareData(): String {
        return "Movie Name\t\t: $name \nDate Realise\t\t: $date \nActor Names\t\t: $actor"
    }
}