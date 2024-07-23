package com.andrecomegno.sescgmf.message

import android.content.Context
import com.andrecomegno.sescgmf.R
import com.google.auth.oauth2.GoogleCredentials
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.io.InputStream

object NotificationSender {
    private const val FCM_URL = "https://fcm.googleapis.com/v1/projects/sesc-gmf/messages:send"
    private const val SCOPED = "https://www.googleapis.com/auth/firebase.messaging"

    private val client = OkHttpClient()
    private val gson = Gson()

    private suspend fun getAccessToken(context: Context): String = withContext(Dispatchers.IO) {
        val inputStream: InputStream = context.resources.openRawResource(R.raw.client_secret)
        val credentials = GoogleCredentials.fromStream(inputStream)
            .createScoped(listOf(SCOPED))
        credentials.refreshIfExpired()
        val accessToken = credentials.accessToken
        accessToken?.tokenValue ?: throw IOException(context.getString(R.string.alert_failed_to_obtain_access_token))
    }
    suspend fun sendLogoutNotification(context: Context, token: String) {
        withContext(Dispatchers.IO) {
            val accessToken = getAccessToken(context)
            val json = gson.toJson(mapOf(
                "message" to mapOf(
                    "token" to token,
                    "data" to mapOf(
                        "action" to "logout"
                    ),
                    "android" to mapOf(
                        "priority" to "high"
                    )
                )
            ))

            val body = json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val request = Request.Builder()
                .url(FCM_URL)
                .post(body)
                .addHeader("Authorization", "Bearer $accessToken")
                .addHeader("Content-Type", "application/json")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    val errorBody = response.body?.string()
                    println("Erro ao enviar notificação: $errorBody")
                } else {
                    println("Notificação enviada com sucesso.")
                }
            }
        }
    }

}
