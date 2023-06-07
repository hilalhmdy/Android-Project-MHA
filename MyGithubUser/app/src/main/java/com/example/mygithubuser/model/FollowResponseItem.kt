package com.example.mygithubuser.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


@Parcelize
data class FollowResponse(

	@field:SerializedName("following_url")
	val followingUrl: String,

	@field:SerializedName("login")
	val username: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("followers_url")
	val followersUrl: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("html_url")
	val htmlUrl: String,

) : Parcelable
