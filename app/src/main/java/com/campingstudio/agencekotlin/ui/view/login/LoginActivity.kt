package com.campingstudio.agencekotlin.ui.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.widget.doAfterTextChanged
import com.campingstudio.agencekotlin.R
import com.campingstudio.agencekotlin.core.AuthUserHelper
import com.campingstudio.agencekotlin.data.model.AuthUser
import com.campingstudio.agencekotlin.databinding.ActivityLoginBinding
import com.campingstudio.agencekotlin.core.AuthManager
import com.campingstudio.agencekotlin.ext.hideKeyboard
import com.campingstudio.agencekotlin.ext.toastLong
import com.campingstudio.agencekotlin.AgenceActivity
import com.campingstudio.agencekotlin.ext.toastShort
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

import java.util.*


class LoginActivity : AuthManager() {
    companion object {
        const val TAG = "LoginActivity"
    }
    private lateinit var binding: ActivityLoginBinding
    private lateinit var vmLoginViewModel: LoginViewModel
    private lateinit var authUserHelper : AuthUserHelper
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var faceBookCallbackManager: CallbackManager
    private val code = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vmLoginViewModel = LoginViewModel()
        authUserHelper = AuthUserHelper(this)
        faceBookCallbackManager = CallbackManager.Factory.create()

        googleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build())
        initProps()
        setupLiveData()
        refreshState(LoginState(isDataValid = true))
    }

    private fun setupLiveData() {
        vmLoginViewModel.loginState.observe(this, {
            it.isDataValid.apply {
                binding.btLogin.isEnabled = this
            }
            refreshState(it)
        })
        vmLoginViewModel.authFailedResponse.observe(this, {
            if (it != null) {
                toastLong(it)
            }
        })
        vmLoginViewModel.loginResponse.observe(this, {
            if (it != null) {
                authUserHelper.saveUser(it)
                authUserHelper.saveLogin(true)
                val intent = Intent(this@LoginActivity, AgenceActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        })

        vmLoginViewModel.firebaseUser.observe(this, {
            if (it != null) {
                val user = AuthUser(it.uid,it.email.toString(),it.displayName.toString())
                authUserHelper.saveUser(user)
                authUserHelper.saveLogin(true)
                try {
                    LoginManager.getInstance().logOut()
                    googleSignInClient.signOut()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                toastLong("Welcome " + it.displayName.toString())
                goHome()
            }
        })

        vmLoginViewModel.errorInsertResponse.observe(this, {
            if (it != null) {
                toastLong(it.message + "")
                if (!it.error) {
                    val intent = Intent(this, AgenceActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                }
            }
        })
    }

    private fun startValidating() {
        vmLoginViewModel.formDataChanged(
            binding.etUserName.text.toString(),
            binding.etPassword.text.toString(),
        )
    }
    private fun refreshState(loginState: LoginState) {
        if (loginState.userError != null) binding.etUserName.error = getString(loginState.userError)
        if (loginState.passwordError != null) binding.etPassword.error = getString(loginState.passwordError)
        binding.btLogin.setBackgroundResource(if(loginState.isDataValid) R.drawable.enable_bg else R.drawable.disable_bg)
    }
    private fun initProps() {
        binding.apply {
            llRoot.setOnClickListener{hideKeyboard()}
            etUserName.doAfterTextChanged {startValidating()}
            etPassword.doAfterTextChanged {startValidating()}
            tvForgotPassword.setOnClickListener {toastShort("Loading...")}
            ivFacebook.setOnClickListener {initFacebookLogin()}
            ivGoogle.setOnClickListener {initGoogleLogin()}
            btLogin.setOnClickListener {goHome()}
        }
    }
    private fun goHome() {
        val intent = Intent(this, AgenceActivity::class.java)
        startActivity(intent)
    }
    private fun initGoogleLogin() {
        // Configure Google Sign In
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, code)
    }

    private fun initFacebookLogin() {
        binding.btFbLoginButton.performClick()
        binding.btFbLoginButton.setReadPermissions("email", "public_profile")
        binding.btFbLoginButton.registerCallback(faceBookCallbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {

            }
            override fun onError(error: FacebookException) {
                toastShort("Error al intentar autenticar con Usuario de Facebook. Intente mas " +
                        "tarde...")
            }
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == code) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken)
            } catch (e: ApiException) {
                toastShort("Error al intentar autenticar con Usuario de Google. Intente mas " +
                        "tarde...")
            }
        } else {
            faceBookCallbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun handleFacebookAccessToken(token: AccessToken?) {
        Log.d(TAG,"handleFacebookAccessToken:$token")
        if (token != null) {
            vmLoginViewModel.facebookLogin(firebaseAuth, token)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
         idToken.let {
             if (it != null) {
                 vmLoginViewModel.googleLogin(firebaseAuth, it)
             }
         }
    }
}





