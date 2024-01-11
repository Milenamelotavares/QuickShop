package com.milenamelo.quickshop

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.content.Context
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle


@Composable
fun HomePage( modifier: Modifier = Modifier,
              viewModel: FavoriteMarketViewModel,
              context: Context) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Bem-vindo ao QuickShop!",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            fontSize = 25.sp
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = "Sua solução inteligente para organização de compras. Aqui estão alguns modelos de listas disponíveis:",
            style = TextStyle(
                fontSize = 20.sp
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        // Botões representando diferentes listas

        ListaButton(nome = "Lista de Mercado", context = LocalContext.current) {
            context.startActivity(
                Intent(context, ListMercadoActivity::class.java).setFlags(
                    Intent.FLAG_ACTIVITY_SINGLE_TOP
                )
            )
        }

        Spacer(modifier = Modifier.size(20.dp))

        ListaButton(nome = "Lista de Farmácia", context = LocalContext.current) {

            context.startActivity(
                Intent(context, ListFarmaciaActivity::class.java).setFlags(
                    Intent.FLAG_ACTIVITY_SINGLE_TOP
                )
            )
        }
        Spacer(modifier = Modifier.size(20.dp))

        ListaButton(nome = "Lista de Frutas e Verduras",context = LocalContext.current ) {
            context.startActivity(
                Intent(context, ListFrutasVerdurasActivity::class.java).setFlags(
                    Intent.FLAG_ACTIVITY_SINGLE_TOP
                )
            )
        }
        Spacer(modifier = Modifier.size(20.dp))
    }
}


@Composable
fun ListaButton(nome: String, context: Context, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .clickable { onClick.invoke() }
            .background(Color(0xFFCA8BD8))
            .fillMaxWidth(0.7f)
            .height(100.dp) // Defina a altura desejada
            .padding(12.dp) // Ajuste o preenchimento conforme necessário
            .clip(RoundedCornerShape(20.dp)) // Borda arredondada
    ) {
        Text(
            text = nome,
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

