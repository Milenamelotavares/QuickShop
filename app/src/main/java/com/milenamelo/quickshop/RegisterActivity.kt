package com.milenamelo.quickshop


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.milenamelo.quickshop.ui.theme.QuickShopTheme
import com.milenamelo.quickshop.ui.theme.Rosa

class RegisterActivity : ComponentActivity() {
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
                    RegisterPage()

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
fun RegisterPage(modifier: Modifier = Modifier) {
    var nome by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var senha by rememberSaveable { mutableStateOf("") }
    var reptsenha by rememberSaveable { mutableStateOf("") }
    val activity = LocalContext.current as? Activity
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(

            text = "Bem-vindo ao QuickShop!",
            fontSize = 24.sp
        )

        OutlinedTextField(
            value = nome,
            label = { Text(text = "Digite seu nome") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Rosa,  // Opcional: Defina a cor do contorno quando o campo está focado
                unfocusedBorderColor = Color.Black  // Opcional: Defina a cor do contorno quando o campo não está focado
            ),
            onValueChange = { nome = it }
        )

        Spacer(modifier = Modifier.size(24.dp))

        OutlinedTextField(
            value = email,
            label = { Text(text = "Digite seu e-mail") },
            modifier = Modifier.fillMaxWidth(),
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
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Rosa,  // Opcional: Defina a cor do contorno quando o campo está focado
                unfocusedBorderColor = Color.Black  // Opcional: Defina a cor do contorno quando o campo não está focado
            ),
            onValueChange = { senha = it },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.size(24.dp))

        OutlinedTextField(
            value = reptsenha,
            label = { Text(text = "Confirmar senha") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Rosa,  // Opcional: Defina a cor do contorno quando o campo está focado
                unfocusedBorderColor = Color.Black  // Opcional: Defina a cor do contorno quando o campo não está focado
            ),
            onValueChange = { reptsenha = it },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.size(24.dp))

        Row(modifier = modifier) {
            Button(
                enabled = email.isNotEmpty() && senha.isNotEmpty() && nome.isNotEmpty() && reptsenha.isNotEmpty() && senha == reptsenha,
                onClick = {
                    if (senha == reptsenha) {
                        // Aqui está a adição da lógica de registro com o Firebase Authentication
                        Firebase.auth.createUserWithEmailAndPassword(email, senha)
                            .addOnCompleteListener(activity!!) { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(activity, "Registro OK!", Toast.LENGTH_LONG).show()
                                    activity?.startActivity(
                                        Intent(
                                            activity,
                                            LoginActivity::class.java
                                        ).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                    )
                                    //activity?.finish()
                                } else {
                                    Toast.makeText(activity, "Registro FALHOU!", Toast.LENGTH_LONG).show()
                                }
                            }
                    } else {
                        Toast.makeText(activity, "As senhas não coincidem", Toast.LENGTH_LONG).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(Rosa)
            ) {
                Text("Registrar")
            }

            Button(
                onClick = { nome= ""; email = ""; senha = ""; reptsenha=""},
                colors = ButtonDefaults.buttonColors(Rosa)
            ) {
                Text("Limpar")
            }
        }
    }
}