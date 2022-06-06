package com.campingstudio.agencekotlin.ui.view.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.campingstudio.agencekotlin.R
import com.campingstudio.agencekotlin.data.model.AuthResponse
import com.campingstudio.agencekotlin.data.model.AuthUser
import com.facebook.AccessToken
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class LoginViewModel : ViewModel() {
    companion object {
        const val TAG = "LoginVM"
    }

    private val _loginState = MutableLiveData(LoginState())
    val loginState: LiveData<LoginState> = _loginState

    var firebaseUser = MutableLiveData<FirebaseUser?>()
    var loginResponse = MutableLiveData<AuthUser>()
    var errorInsertResponse = MutableLiveData<AuthResponse>()
    var authFailedResponse = MutableLiveData<String>()

    fun facebookLogin(firebaseAuth: FirebaseAuth, token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {
                val user : FirebaseUser? = firebaseAuth.currentUser
                firebaseUser.value = user
            }.addOnFailureListener {
                authFailedResponse.value = it.message
                Log.d(TAG,"addOnFailureListener:" + it.message)
            }
    }

    fun googleLogin(firebaseAuth: FirebaseAuth, token: String) {
        val credential = GoogleAuthProvider.getCredential(token, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {
                val user = firebaseAuth.currentUser
                firebaseUser.value = user
            }.addOnFailureListener {
                authFailedResponse.value = it.message
                Log.d(TAG,"addOnFailureListener:" + it.message)
            }
    }

    fun formDataChanged(userName: String,password: String ) {
        if (!isUserNameValid(userName)){
              _loginState.value = LoginState(userError = R.string.invalid_username,isDataValid =false)
        } else
            if (!isPasswordValid(password)) {
                _loginState.value = LoginState(passwordError = R.string.invalid_password,isDataValid =false)
            } else
            {
                _loginState.value = LoginState(isDataValid = true)
            }
    }
    private fun isUserNameValid(id: String): Boolean {
        return id.isNotEmpty()
    }
    private fun isPasswordValid(loftName: String): Boolean {
        return loftName.isNotEmpty()
    }
}