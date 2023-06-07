package com.example.storyapp.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.R
import com.example.storyapp.model.data.UserModel
import com.example.storyapp.model.data.UserPreference
import com.example.storyapp.model.paging.Result
import com.example.storyapp.view.custom.MyEditTextEmail
import com.example.storyapp.view.helper.ViewModelFactory
import com.example.storyapp.view.main.MainActivity
import com.example.storyapp.view.register.RegisterActivity
import com.example.storyapp.databinding.ActivityLoginBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel : LoginViewModel
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progresbar.visibility = View.INVISIBLE

        setupView()
        setupViewModel()
        setupAction()
        playAnimation()

        Log.d("Eror", "eror login" )
    }

    private fun playAnimation(){
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val storyTittle = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val loginTitle = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(500)
        val emailTv = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailEdit = ObjectAnimator.ofFloat(binding.emailEditText, View.ALPHA, 1f).setDuration(500)
        val passwordTv = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordEdit = ObjectAnimator.ofFloat(binding.passwordEditText, View.ALPHA, 1f).setDuration(500)
        val registerButton = ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 1f).setDuration(500)
        val loginButton = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(storyTittle,loginTitle, emailTv, emailEdit, passwordTv, passwordEdit, loginButton, registerButton)
            start()
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun getLogin(context: Context): Boolean{
        val user : UserModel
        runBlocking {
            user = UserPreference.getInstance(context.dataStore).getUser().first()
        }

        Log.d("logn", user.isLogin.toString())
        return user.isLogin
    }

    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(this)
        )[LoginViewModel::class.java]

        if (getLogin(this)){
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            when{
                email.isEmpty() -> {
                    binding.emailEditText.error = getString(R.string.email_error)
                }
                password.isEmpty() -> {
                    binding.passwordEditText.error = getString(R.string.password_error)
                }
                password.length <= 7 -> {
                    AlertDialog.Builder(this).apply {
                        setMessage(getString(R.string.password_input_error))
                        create()
                        show()
                    }
                }
                MyEditTextEmail.flag.check == false -> {
                    AlertDialog.Builder(this).apply {
                        setMessage(getString(R.string.email_input_error))
                        create()
                        show()
                    }
                }
                else -> {
                    loginViewModel.loginAPI(email, password).observe(this){
                        if(it != null){
                            when(it){
                                is Result.Loading -> {
                                    showLoading(true)
                                }
                                is Result.Success -> {
                                    showLoading(false)

                                    Toast.makeText(
                                        this,
                                        getString(R.string.login_success),
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                    finish()
                                }
                                is Result.Error -> {
                                    showLoading(false)
                                    AlertDialog.Builder(this@LoginActivity).apply {
                                        setMessage(getString(R.string.email_input_error))
                                        create()
                                        show()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        binding.registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progresbar.visibility = if (isLoading) View.VISIBLE else View.GONE

    }
}