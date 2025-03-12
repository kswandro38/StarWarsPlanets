package com.kalanasarange.starwarsplanets.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Data class representing a response containing a list of planets.
 *
 * @param count - The total number of planets.
 * @param next - The URL for the next page of planets (if available) or null
 * @param previous - The URL for the previous page of planets (if available) or null
 * @param planets - A list of [Planet] objects received in the response.
 */
@Parcelize
data class PlanetListResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val planets: ArrayList<Planet>
) : Parcelable