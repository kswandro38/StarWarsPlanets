package com.kalanasarange.starwarsplanets.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.kalanasarange.starwarsplanets.BuildConfig
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

/**
 * Data class representing a Planet.
 *
 * @param name - The name of the planet
 * @param rotationPeriod - The rotation period of the planet
 * @param orbitalPeriod - The orbital period of the planet
 * @param diameter - The diameter of the planet
 * @param climate - The climate of the planet
 * @param gravity - The gravity of the planet
 * @param terrain - The terrain of the planet
 * @param surfaceWater - The surface water of the planet
 * @param population - The population of the planet
 * @param url - The URL of the planet
 */
@Parcelize
data class Planet(
    @SerializedName("name") val name: String,
    @SerializedName("rotation_period") val rotationPeriod: String,
    @SerializedName("orbital_period") val orbitalPeriod: String,
    @SerializedName("diameter") val diameter: String,
    @SerializedName("climate") val climate: String,
    @SerializedName("gravity") val gravity: String,
    @SerializedName("terrain") val terrain: String,
    @SerializedName("surface_water") val surfaceWater: String,
    @SerializedName("population") val population: String,
    @SerializedName("url") val url: String
): Parcelable {
    val id: Int
        get() = getLastDigitFromUrl()

    val thumbnailUrl: String
        get() = "${BuildConfig.IMAGES_BASE_URL}id/$id/100"

    val imageUrl: String
        get() = "${BuildConfig.IMAGES_BASE_URL}id/$id/800/600"

    fun getLastDigitFromUrl(): Int {
        // Regex to find the last digits
        val regex = Regex("/(\\d+)/$")
        val matchResult = regex.find(url)
        return matchResult?.groups?.get(1)?.value?.toIntOrNull() ?: Random.nextInt(100, 1000)
    }
}