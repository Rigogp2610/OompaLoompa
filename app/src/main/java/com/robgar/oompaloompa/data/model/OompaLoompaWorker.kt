package com.robgar.oompaloompa.data.model

import com.google.gson.annotations.SerializedName

data class OompaLoompaWorker (
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("favorite")
    val favorite: Favorite,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("profession")
    val profession: String,
    @SerializedName("quota")
    val quota: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("age")
    val age: Int,
    @SerializedName("country")
    val country: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("id")
    val id: Int
)