package com.campingstudio.agencekotlin.core
import android.content.Context
import android.content.SharedPreferences
import com.campingstudio.agencekotlin.data.model.AuthUser
import com.google.gson.Gson


class AuthUserHelper(context: Context) {
    private val sharedPreferences: SharedPreferences
    private val sharedPreferencesEditor: SharedPreferences.Editor
    private val entryName = "com.campingstudio.agencekotlin"
    private val fieldNameAuthUser = "AuthUser"

    private var fieldNameIsLogin = "login"
    fun saveLogin(value: Boolean?) {
        if (value != null) {
            sharedPreferencesEditor.putBoolean(fieldNameIsLogin, value)
        }
        sharedPreferencesEditor.apply()
    }

    val login: Boolean
        get() = sharedPreferences.getBoolean(fieldNameIsLogin, false)

    fun saveUser(user: AuthUser?) {
        sharedPreferencesEditor.putString(fieldNameAuthUser, Gson().toJson(user))
        sharedPreferencesEditor.apply()
    }

    val user: AuthUser?
        get() {
            val userString: String = sharedPreferences.getString(fieldNameAuthUser, "").toString()
            return if (userString.isNotEmpty()) {
                Gson().fromJson(userString, AuthUser::class.java)
            } else null
        }


    fun logout() {
        try {
            sharedPreferencesEditor.clear().apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        saveLogin(false)
    }

    init {
        sharedPreferences = context.getSharedPreferences(entryName, Context.MODE_PRIVATE)
        sharedPreferencesEditor = sharedPreferences.edit()
    }
}