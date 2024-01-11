package com.milenamelo.quickshop


import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class FavoriteMarketViewModel : ViewModel() {
    private val _marketies = getFavoriteMarketies().toMutableStateList()
    val cities: List<FavoriteCity>
        get() = _marketies
    fun remove(city: FavoriteCity) {
        _marketies.remove(city)
    }
    fun add(city: String, location: LatLng? = null) {
        _marketies.add(
            FavoriteCity(
                city, "Carregando preço...", location = location
            )
        )
    }
}

data class FavoriteCity(
    val cityName: String,
    var current: String,
    var location: LatLng? = null) {

        var isChecked: Boolean = false

}

fun getFavoriteMarketies() = List(30) {
        i -> FavoriteCity(cityName = "Produto $i", current= "Carregando preço...") }
