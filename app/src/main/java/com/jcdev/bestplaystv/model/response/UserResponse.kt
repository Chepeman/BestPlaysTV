package com.jcdev.bestplaystv.model.response

import com.google.gson.annotations.SerializedName
import com.jcdev.bestplaystv.model.User

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