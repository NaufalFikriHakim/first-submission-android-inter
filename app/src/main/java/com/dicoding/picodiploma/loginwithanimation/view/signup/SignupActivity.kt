package com.dicoding.picodiploma.loginwithanimation.view.signup

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.RegisterResponse
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivitySignupBinding
import com.dicoding.picodiploma.loginwithanimation.view.UserViewModel
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
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
        binding.signupButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val name = binding.nameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
            val viewModel: UserViewModel by viewModels {
                factory
            }

            lifecycleScope.launch {
                try {
                    //get success message
                    val message = viewModel.register(name, email, password).message
                    message?.let { showSuccessDialog(email) }
                } catch (e: HttpException) {
                    //get error message
                    val jsonInString = e.response()?.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonInString, RegisterResponse::class.java)
                    val errorMessage = errorBody.message
                    errorMessage?.let {showErrorDialog(it)}
                }
            }
        }
    }

    private fun showSuccessDialog(email: String) {
        // Using 'this@MainActivity' to get the Context within an Activity
        AlertDialog.Builder(this).apply {
            setTitle("Yeah!")
            setMessage("Akun dengan $email sudah jadi nih. Yuk, login dan belajar coding.")
            setPositiveButton("Lanjut") { _, _ ->
                finish()
            }
            create()
            show()
        }
    }

    private fun showErrorDialog(errorMessage: String) {
        // Using 'this@MainActivity' to get the Context within an Activity
        AlertDialog.Builder(this).apply {
            setTitle("Error")
            setMessage(errorMessage)
            setPositiveButton("OK", null)
            create()
            show()
        }
    }
}