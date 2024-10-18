package com.example.weather.base

import android.content.Context
import android.content.SharedPreferences
import com.example.weather.data.response.ReverseGeoCodeResponse
import com.google.gson.Gson

class PreferencesManager(private val context: Context) {

    companion object{
        const val SAVED_LOCATION = "saved_location"
    }
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    var savedLocation: ReverseGeoCodeResponse?
        get() = modelFromJson<ReverseGeoCodeResponse>(
            sharedPreferences.getString(SAVED_LOCATION, "") ?: ""
        )
        set(site) {
            sharedPreferences.edit().putString(SAVED_LOCATION, modelToJson(site)).apply()
        }

    // Decode (Deserialize) a model from JSON
    private inline fun <reified T> modelFromJson(json: String): T? {
        val gson = Gson()
        return gson.fromJson(json, T::class.java)
    }

    // Encode (Serialize) a model to JSON
    private inline fun <reified T> modelToJson(json: T): String {
        val gson = Gson()
        return gson.toJson(json)
    }

}