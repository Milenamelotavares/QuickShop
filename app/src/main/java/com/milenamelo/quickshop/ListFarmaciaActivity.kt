package com.milenamelo.quickshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.milenamelo.quickshop.ui.theme.QuickShopTheme

class ListFarmaciaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuickShopTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PharmacyList()
                }
            }
        }
    }
}

@Composable
fun PharmacyList() {
    val pharmacyList = listOf(
        "Analgésico",
        "Antitérmico",
        "Antiácido",
        "Anti-histamínico",
        "Descongestionante nasal",
        "Antisséptico",
        "Curativo",
        "Protetor solar"
    )

    // Estado para rastrear os itens selecionados
    var selectedItems by remember { mutableStateOf(emptyList<String>()) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(pharmacyList) { item ->
            val isChecked = selectedItems.contains(item)

            PharmacyListItem(
                item = item,
                isChecked = isChecked,
                onCheckedChange = {
                    selectedItems = if (isChecked) {
                        selectedItems - item
                    } else {
                        selectedItems + item
                    }
                }
            )
        }
    }
}

@Composable
fun PharmacyListItem(item: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onCheckedChange(!isChecked) }
    ) {
        // Checkbox
        Checkbox(
            checked = isChecked,
            onCheckedChange = null
        )

        // Texto do item
        Text(
            text = item,
            modifier = Modifier
                .padding(16.dp)
                .weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PharmacyListPreview() {
    QuickShopTheme {
        PharmacyList()
    }
}
