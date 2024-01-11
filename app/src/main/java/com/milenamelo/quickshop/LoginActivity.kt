package com.milenamelo.quickshop


import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.milenamelo.quickshop.ui.theme.QuickShopTheme
import com.milenamelo.quickshop.ui.theme.Rosa


class LoginActivity : ComponentActivity() {
    private lateinit var fbAuthList: FBAuthListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.fbAuthList = FBAuthListener(this)

        setContent {
            QuickShopTheme {
                // A surface container using the 'background' color from the theme

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginPage()
                }
            }
        }
    }
    override fun onStart() {
        super.onStart()
        Firebase.auth.addAuthStateListener(fbAuthList)
    }
    override fun onStop() {
        super.onStop()
        Firebase.auth.removeAuthStateListener(fbAuthList)
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun LoginPage(modifier: Modifier = Modifier) {
    var email by rememberSaveable { mutableStateOf("") }
    var senha by rememberSaveable { mutableStateOf("") }
    val activity = LocalContext.current as? Activity
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = "QuickShop",
            style = TextStyle(
              //  fontFamily = FontFamily(R.font.), // Substitua 'font' pelo nome real do seu arquivo de fonte
                fontWeight = FontWeight.Bold,
                fontSize = 48.sp // Ajuste conforme necessário
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Image(
            painter = painterResource(id = com.milenamelo.quickshop.R.drawable.carrinho),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(0.3f) // Ajuste conforme necessário
                .aspectRatio(1.0f)
                .padding(10.dp),
            contentScale = ContentScale.Crop
        )


        Spacer(modifier = Modifier.size(20.dp))

        OutlinedTextField(
            value = email,
            label = { Text(text = "Digite seu e-mail") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Rosa,  // Opcional: Defina a cor do contorno quando o campo está focado
                unfocusedBorderColor = Color.Black  // Opcional: Defina a cor do contorno quando o campo não está focado
            ),
            onValueChange = { email = it }
        )
        Spacer(modifier = Modifier.size(24.dp))

        OutlinedTextField(
            value = senha,
            label = { Text(text = "Digite sua senha") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Rosa,  // Opcional: Defina a cor do contorno quando o campo está focado
                unfocusedBorderColor = Color.Black  // Opcional: Defina a cor do contorno quando o campo não está focado
            ),
            onValueChange = { senha = it },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.size(24.dp))

        Row(modifier = modifier) {
            Button(
                enabled = email.isNotEmpty() && senha.isNotEmpty(),
                onClick = {
                    val auth = FirebaseAuth.getInstance()
                    auth.signInWithEmailAndPassword(email, senha)
                        .addOnCompleteListener(activity!!) { task ->
                            if (task.isSuccessful) {
                               /* activity?.startActivity(
                                    Intent(activity, MainActivity::class.java)
                                        .setFlags(FLAG_ACTIVITY_SINGLE_TOP)
                                )*/
                                Toast.makeText(activity, "Login OK!", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(activity, "Login FALHOU!", Toast.LENGTH_LONG).show()
                            }
                        }
                },
                colors = ButtonDefaults.buttonColors(Rosa)
            ) {
                Text("Login")
            }

            Button(
                onClick = {

                    activity?.startActivity(
                        Intent(
                            activity,
                            RegisterActivity::class.java
                        ).setFlags(FLAG_ACTIVITY_SINGLE_TOP)

                    )
                },
                        colors = ButtonDefaults.buttonColors(Rosa)


            ) {
                Text("Registra-se")
            }


            Button(
                onClick = { email = ""; senha = "" },
                colors = ButtonDefaults.buttonColors(Rosa)


            ) {
                Text("Limpar")
            }
        }
    }


}

