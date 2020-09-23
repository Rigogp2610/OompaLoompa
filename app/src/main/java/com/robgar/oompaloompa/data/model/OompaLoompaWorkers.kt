package com.robgar.oompaloompa.data.model

import com.google.gson.annotations.SerializedName

data class OompaLoompaWorkers (
    @SerializedName("current")
    val currentPage: Int,
    @SerializedName("total")
    val totalPages: Int,
    @SerializedName("results")
    val workers: List<Worker>
)