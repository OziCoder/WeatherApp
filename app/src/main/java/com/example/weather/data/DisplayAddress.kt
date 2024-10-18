package com.example.weather.data

class DisplayAddress(
    val name:String,
    val state:String,
    var country:String
) {
    fun getFullAddress():String{
        return "$name\n$state, $country"
    }
}