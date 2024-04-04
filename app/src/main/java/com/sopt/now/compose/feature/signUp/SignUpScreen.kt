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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sopt.now.compose.model.User
import kotlinx.coroutines.launch

val Context.dataStore by preferencesDataStore(name = "userPreferences")

@Composable
fun SignUpScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var drinkCapacity by remember { mutableStateOf("") }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(24.dp)) {
        Text(text = "회원가입")
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("아이디") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("비밀번호") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        OutlinedTextField(
            value = nickname,
            onValueChange = { nickname = it },
            label = { Text("닉네임") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        OutlinedTextField(
            value = drinkCapacity,
            onValueChange = { drinkCapacity = it },
            label = { Text("주량") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
        )
        Button(
            onClick = {
                when {
                    username.length !in 6..10 -> Toast.makeText(
                        context,
                        "ID는 6~10 글자여야 합니다.",
                        Toast.LENGTH_SHORT
                    ).show()

                    password.length !in 8..12 -> Toast.makeText(
                        context,
                        "비밀번호는 8~12 글자여야 합니다.",
                        Toast.LENGTH_SHORT
                    ).show()

                    nickname.isBlank() -> Toast.makeText(
                        context,
                        "닉네임은 공백일 수 없습니다.",
                        Toast.LENGTH_SHORT
                    ).show()

                    drinkCapacity.isBlank() -> Toast.makeText(
                        context,
                        "주량을 입력해주세요.",
                        Toast.LENGTH_SHORT
                    ).show()

                    else -> {
                        val user = User(username, password, nickname, drinkCapacity.toFloat())
                        coroutineScope.launch { context.saveUserToPreferences(user) }
                        Toast.makeText(context, "회원가입에 성공하셨습니다!", Toast.LENGTH_SHORT).show()
                        val activity = (context as SignUpActivity)
                        activity.finish()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("회원가입")
        }
    }
}

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