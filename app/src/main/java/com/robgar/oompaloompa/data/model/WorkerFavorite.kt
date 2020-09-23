package com.robgar.oompaloompa.data.model

import com.google.gson.annotations.SerializedName

class WorkerFavorite(
    @SerializedName("color")
    var color: String,
    @SerializedName("food")
    var food: String,
    @SerializedName("random_string")
    var randomString: String,
    @SerializedName("song")
    var song: String
)