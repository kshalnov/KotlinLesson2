package ru.gb.course1.kotlinlesson2.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.gb.course1.kotlinlesson2.R
import ru.gb.course1.kotlinlesson2.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: LoginContract.ViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUiEvents()
        initViewModel(viewModel)
    }

    private fun initViewModel(viewModel: LoginContract.ViewModel) {

        viewModel.screenStateLiveData.observe(this) { state ->
            when (state) {
                LoginContract.LoginState.IDLE -> {

                }
                LoginContract.LoginState.SUCCESS -> {
                    Toast.makeText(baseContext, "Ура!", Toast.LENGTH_SHORT).show()
                }
                LoginContract.LoginState.NO_INTERNET,
                LoginContract.LoginState.WRONG_PASSWORD -> {
                    Toast.makeText(baseContext, state.getHumanReadableString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        viewModel.emailLiveData.observe(this) { email ->
            // binding.emailEditText.setText(email)
            binding.emailTextView.text = email
        }
        viewModel.loginEnabledLiveData.observe(this) { enable ->
            binding.loginButton.isEnabled = enable
        }
        viewModel.emailValidationErrorLiveData.observe(this) {
            binding.emailEditText.error = if (it) "Не валидный емейл" else null
        }
    }

    private fun initUiEvents() {
        binding.loginButton.setOnClickListener {
            viewModel.onLogin(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
                viewModel.onEmailChanged(text.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun LoginContract.LoginState.getHumanReadableString() = getString(
        when (this) {
            LoginContract.LoginState.NO_INTERNET -> R.string.error_no_internet
            LoginContract.LoginState.WRONG_PASSWORD -> R.string.error_wrong_password
            else -> throw RuntimeException("Это не ошибка")
        }
    )
}