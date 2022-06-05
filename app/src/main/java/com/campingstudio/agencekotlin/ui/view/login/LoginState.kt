package com.campingstudio.agencekotlin.ui.view.login

data class LoginState (
    val userError : Int? = null,
    val passwordError : Int? = null,
    val isDataValid: Boolean = false
)