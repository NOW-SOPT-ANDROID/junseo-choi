package com.sopt.now.compose.feature.signUp

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import com.sopt.now.compose.R
import com.sopt.now.compose.model.Screen
import com.sopt.now.compose.model.User
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import kotlinx.coroutines.launch

val Context.dataStore by preferencesDataStore(name = "userPreferences")

@Composable
fun SignUpScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var drinkCapacity by remember { mutableStateOf("") }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(24.dp)) {
        Text(text = stringResource(id = R.string.sign_up_title))
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(stringResource(id = R.string.username_label)) },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(id = R.string.password_label)) },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )
        OutlinedTextField(
            value = nickname,
            onValueChange = { nickname = it },
            label = { Text(stringResource(id = R.string.nickname_label)) },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )
        OutlinedTextField(
            value = drinkCapacity,
            onValueChange = { drinkCapacity = it },
            label = { Text(stringResource(id = R.string.drink_capacity_label)) },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
            keyboardOptions =
                KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                ),
        )
        Button(
            onClick = {
                when {
                    username.length !in MIN_USERNAME_LENGTH..MAX_USERNAME_LENGTH ->
                        Toast.makeText(
                            context,
                            context.getString(R.string.username_error),
                            Toast.LENGTH_SHORT,
                        ).show()

                    password.length !in MIN_PASSWORD_LENGTH..MAX_PASSWORD_LENGTH ->
                        Toast.makeText(
                            context,
                            context.getString(R.string.password_error),
                            Toast.LENGTH_SHORT,
                        ).show()

                    nickname.isBlank() ->
                        Toast.makeText(
                            context,
                            context.getString(R.string.nickname_error),
                            Toast.LENGTH_SHORT,
                        ).show()

                    drinkCapacity.isBlank() ->
                        Toast.makeText(
                            context,
                            context.getString(R.string.drink_capacity_error),
                            Toast.LENGTH_SHORT,
                        ).show()

                    else -> {
                        val user = User(username, password, nickname, drinkCapacity.toFloat())
                        coroutineScope.launch { context.saveUserToPreferences(user) }
                        Toast.makeText(
                            context,
                            context.getString(R.string.sign_up_success),
                            Toast.LENGTH_SHORT,
                        ).show()
                        navController.navigate(Screen.SignIn.route)
                    }
                }
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
        ) {
            Text(stringResource(id = R.string.sign_up_button))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpPreview() {
    NOWSOPTAndroidTheme {
        SignUpScreen(navController = NavController(LocalContext.current))
    }
}

private const val MIN_USERNAME_LENGTH = 6
private const val MAX_USERNAME_LENGTH = 10
private const val MIN_PASSWORD_LENGTH = 8
private const val MAX_PASSWORD_LENGTH = 12

suspend fun Context.saveUserToPreferences(user: User) {
    val usernameKey = stringPreferencesKey("username")
    val passwordKey = stringPreferencesKey("password")
    val nicknameKey = stringPreferencesKey("nickname")
    val drinkCapacityKey = stringPreferencesKey("drinkCapacity")

    dataStore.edit { preferences ->
        preferences[usernameKey] = user.username
        preferences[passwordKey] = user.password
        preferences[nicknameKey] = user.nickname
        preferences[drinkCapacityKey] = user.drinkCapacity.toString()
    }
}
