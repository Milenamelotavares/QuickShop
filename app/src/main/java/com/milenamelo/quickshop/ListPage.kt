package com.milenamelo.quickshop


import android.app.Activity

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.milenamelo.quickshop.ui.theme.Rosa

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ListPage(modifier: Modifier = Modifier,
             viewModel: FavoriteMarketViewModel,
             context: Context) {
    var newItemName by rememberSaveable { mutableStateOf("") }

    // Usando remember para lembrar da lista de compras
    var shoppingList by rememberSaveable {
        mutableStateOf(
            mutableListOf(
                ShoppingItem(name = "Item 1"),
                ShoppingItem(name = "Item 2"),
                ShoppingItem(name = "Item 3")
            )
        )
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Crie sua Lista",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.size(16.dp))

        // Campo para adicionar novo item
        OutlinedTextField(
            value = newItemName,
            onValueChange = { newItemName = it },
            label = { Text("Nome do Produto") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Rosa,  // Opcional: Defina a cor do contorno quando o campo está focado
                unfocusedBorderColor = Color.Black  // Opcional: Defina a cor do contorno quando o campo não está focado
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (newItemName.isNotEmpty()) {
                        // Adicionando novo item à lista de compras
                        shoppingList.add(ShoppingItem(name = newItemName))
                        newItemName = ""
                        keyboardController?.hide()

                        // Exibindo Toast para indicar que o item foi adicionado
                        Toast.makeText(context, "Item adicionado à lista!", Toast.LENGTH_SHORT).show()


                    }
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.size(16.dp))

        // Lista de itens
        ShoppingList(shoppingList) { item ->
            // Removendo item diretamente da lista de compras
            shoppingList.remove(item)

            // Exibindo Toast para indicar que o item foi removido
            Toast.makeText(context, "Item removido da lista!", Toast.LENGTH_SHORT).show()


        }
    }
}

@Composable
fun ShoppingList(
    shoppingList: List<ShoppingItem>,
    onItemRemove: (ShoppingItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(shoppingList) { item ->
            ShoppingListItem(item = item, onRemoveClick = { onItemRemove(item) })
        }
    }
}

@Composable
fun ShoppingListItem(
    item: ShoppingItem,
    onRemoveClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = item.isChecked,
            onCheckedChange = { checked ->
                item.isChecked = checked
            },
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = item.name,
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onRemoveClick) {
            Icon(Icons.Filled.Close, contentDescription = "Remove")
        }
    }
}

data class ShoppingItem(val name: String, var isChecked: Boolean = false)

// Toast em Compose
@Composable
fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
