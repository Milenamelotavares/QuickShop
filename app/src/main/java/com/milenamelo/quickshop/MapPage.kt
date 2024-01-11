package com.milenamelo.quickshop

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun MapPage(
    modifier: Modifier = Modifier,
    viewModel: FavoriteMarketViewModel,
    context: Context,
    camPosState: CameraPositionState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {
        val NovoAtacarejo = LatLng(-8.05, -34.9)
        val MixMatheus = LatLng(-8.09, -34.9)
        val Atacadao = LatLng(-8.12, -34.9)
        val Extra = LatLng(-8.12, -34.8)
        val SuperBomPreco= LatLng(-8.10, -34.8)

        val hasLocationPermission by remember {
            mutableStateOf(
                ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED
            )
        }

        GoogleMap (
            modifier = Modifier.fillMaxSize(),
            onMapClick = { viewModel.add("Nova produto", location = it) },
            properties = MapProperties(isMyLocationEnabled = hasLocationPermission),
            uiSettings = MapUiSettings(myLocationButtonEnabled = true),
            cameraPositionState = camPosState
        ) {
            viewModel.cities.forEach {
                if (it.location != null) {
                    Marker( state = MarkerState(position = it.location!!),
                        title = "${it.cityName}", snippet = "${it.location}")
                }
            }

            Marker(
                state = MarkerState(position = NovoAtacarejo),
                title = "NovoAtacarejo",
                snippet = "Arroz está: \$6,50",
                icon = BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_BLUE)
            )

            Marker(
                state = MarkerState(position = MixMatheus),
                title = "MixMatheus",
                snippet = "Arroz está: \$5,40",
                icon = BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_AZURE)
            )

            Marker(
                state = MarkerState(position = Atacadao),
                title = "Atacadão",
                snippet = "Feijão está: \$8,90",
                icon = BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_GREEN)
            )

            Marker(
                state = MarkerState(position = Extra),
                title = "Extra",
                snippet = "Feijão está: \$11,50",
                icon = BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_GREEN)
            )

            Marker(
                state = MarkerState(position = SuperBomPreco),
                title = "SuperBomPreco",
                snippet = "Brahma de  269 mL está: \$2,79",
                icon = BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_GREEN)
            )
        }
    }
}