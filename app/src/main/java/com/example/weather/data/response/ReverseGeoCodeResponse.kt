package com.example.weather.data.response

import com.google.gson.annotations.SerializedName

data class ReverseGeoCodeResponse(
    @SerializedName("name") var name: String? = null,
    @SerializedName("local_names") var localNames: LocalNames? = LocalNames(),
    @SerializedName("lat") var lat: Double? = null,
    @SerializedName("lon") var lon: Double? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("state") var state: String? = null
)
data class LocalNames (
    @SerializedName("ar") var ar: String? = null,
    @SerializedName("ascii") var ascii: String? = null,
    @SerializedName("bg") var bg: String? = null,
    @SerializedName("ca") var ca: String? = null,
    @SerializedName("de") var de: String? = null,
    @SerializedName("el") var el: String? = null,
    @SerializedName("en") var en: String? = null,
    @SerializedName("fa") var fa: String? = null,
    @SerializedName("feature_name" ) var featureName : String? = null,
    @SerializedName("fi") var fi: String? = null,
    @SerializedName("fr") var fr: String? = null,
    @SerializedName("gl") var gl: String? = null,
    @SerializedName("he") var he: String? = null,
    @SerializedName("hi") var hi: String? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("it") var it: String? = null,
    @SerializedName("ja") var ja: String? = null,
    @SerializedName("la") var la: String? = null,
    @SerializedName("lt") var lt: String? = null,
    @SerializedName("pt") var pt: String? = null,
    @SerializedName("ru") var ru: String? = null,
    @SerializedName("sr") var sr: String? = null,
    @SerializedName("th") var th: String? = null,
    @SerializedName("tr") var tr: String? = null,
    @SerializedName("vi") var vi: String? = null,
    @SerializedName("zu") var zu: String? = null
)
