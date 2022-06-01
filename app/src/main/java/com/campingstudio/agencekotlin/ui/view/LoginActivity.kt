package com.campingstudio.agencekotlin.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import com.campingstudio.agencekotlin.R
import com.campingstudio.agencekotlin.core.AuthUserHelper
import com.campingstudio.agencekotlin.data.model.AuthUser
import com.campingstudio.agencekotlin.databinding.ActivityLoginBinding
import com.campingstudio.agencekotlin.core.AuthManager
import com.campingstudio.agencekotlin.ext.hideKeyboard
import com.campingstudio.agencekotlin.ui.state.LoginState
import com.campingstudio.agencekotlin.ui.viewmodel.LoginViewModel
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.progressindicator.CircularProgressIndicator

import java.util.*


class LoginActivity : AuthManager() {
    companion object {
        const val TAG = "LoginActivity"
    }
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    private lateinit var binding: ActivityLoginBinding
    private lateinit var vmLoginViewModel: LoginViewModel
    private lateinit var authUserHelper : AuthUserHelper
    private var isValid: Boolean = false
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var faceBookCallbackManager: CallbackManager
    private var RC_SIGN_IN = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initView
        vmLoginViewModel = LoginViewModel()
        authUserHelper = AuthUserHelper(this)
        faceBookCallbackManager = CallbackManager.Factory.create()

        // Configure Google Sign In
        googleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build())
        initProps()
        setupLiveData()
        //only one time
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
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                screenToast(it)
            }
        })
        vmLoginViewModel.loginResponse.observe(this, {
            if (it != null) {
                authUserHelper.saveUser(it)
                authUserHelper.saveLogin(true)
                val intent = Intent(this@LoginActivity, ShoppActivity::class.java)
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
                    //Facebook LogOut
                    LoginManager.getInstance().logOut()
                    //Google SignOut
                    googleSignInClient.signOut()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                screenToast("Welcome "+it.displayName.toString())
                goHome()
            }
        })

        vmLoginViewModel.errorInsertResponse.observe(this, {
            if (it != null) {
                screenToast(it.message + "")
                if (!it.error) {
                    val intent = Intent(this, ShoppActivity::class.java)
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
    private fun refreshState(loginState: LoginState ) {
        if (loginState.userError != null) binding.etUserName.error = getString(loginState.userError)
        if (loginState.passwordError != null) binding.etPassword.error = getString(loginState.passwordError)
        binding.btLogin.setBackgroundResource(if(loginState.isDataValid) R.drawable.enable_bg else R.drawable.disable_bg)
    }
    private fun initProps() {
        binding.apply {
            llRoot.setOnClickListener{hideKeyboard()}
            etUserName.doAfterTextChanged {startValidating()}
            etPassword.doAfterTextChanged {startValidating()}
            tvForgotPassword.setOnClickListener {screenToast("Loading...")}
            ivFacebook.setOnClickListener {initFacebookLogin()}
            ivGoogle.setOnClickListener {initGoogleLogin()}
            btLogin.setOnClickListener {goHome()}
        }
    }
    private fun goHome() {
        val intent = Intent(this, ShoppActivity::class.java)
        startActivity(intent)
    }
    private fun initGoogleLogin() {
        // Configure Google Sign In
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
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
                screenToast("facebook:onError:$error")
            }
        })
    }

    // ...
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                screenToast("Google sign in failed:$e")

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

    private fun screenToast(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}





