package com.example.storyapp.view.register

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityRegisterBinding
import com.example.storyapp.model.paging.Result
import com.example.storyapp.view.custom.MyEditTextEmail
import com.example.storyapp.view.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progresbar.visibility = View.INVISIBLE

        setupView()
        setupAction()

        binding.loginButton.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            Log.d("coba", "AHAHAHA")
            startActivity(intent)
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

    private fun setupAction() {
        binding.registerButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            when {
                username.isEmpty() -> {
                    binding.usernameEditText.error = getString(R.string.username_error)
                }
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
                    registerViewModel.registerAPI(username, email, password).observe(this){
                        if (it != null){
                            when(it){
                                is Result.Loading -> {
                                    showLoading(true)
                                }
                                is Result.Success -> {
                                    showLoading(false)

                                    AlertDialog.Builder(this@RegisterActivity).apply {
                                        setMessage(getString(R.string.register_success))
                                        setPositiveButton(getString(R.string.login)) { _, _ ->
                                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                            startActivity(intent)
                                            finish()
                                        }
                                        create()
                                        show()
                                    }
                                }
                                is Result.Error -> {
                                    showLoading(false)
                                    AlertDialog.Builder(this@RegisterActivity).apply {
                                        setMessage(getString(R.string.register_error))
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
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progresbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}