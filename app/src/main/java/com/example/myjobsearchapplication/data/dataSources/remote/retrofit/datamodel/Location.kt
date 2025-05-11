package com.example.myjobsearchapplication.data.dataSources.remote.retrofit.datamodel

data class Location(
    val display_name: String,
    val area: List<String>,
    val latitude: Double?,
    val longitude: Double?
)