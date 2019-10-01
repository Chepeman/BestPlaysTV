package com.jcdev.bestplaystv.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("content")
    val content: UserContent
)

data class UserContent(
    @SerializedName("user")
    val user: User
)