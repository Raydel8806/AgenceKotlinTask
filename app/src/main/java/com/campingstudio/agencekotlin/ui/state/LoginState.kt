package com.campingstudio.agencekotlin.ui.state

data class LoginState (
    val userError : Int? = null,
    val passwordError : Int? = null,
    val isDataValid: Boolean = false
)