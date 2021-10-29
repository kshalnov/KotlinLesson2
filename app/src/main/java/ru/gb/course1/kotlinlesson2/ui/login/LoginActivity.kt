package ru.gb.course1.kotlinlesson2.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.gb.course1.kotlinlesson2.R
import ru.gb.course1.kotlinlesson2.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity(), LoginContract.View {
    private lateinit var binding: ActivityMainBinding

    private lateinit var presenter: LoginContract.Presenter

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = lastCustomNonConfigurationInstance as? LoginPresenter ?: LoginPresenter()
        presenter.attach(this)

        binding.loginButton.setOnClickListener {
            presenter.onLogin(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
                presenter.onEmailChanged(text.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    override fun showError(errorCode: LoginContract.ErrorCode) {
        Toast.makeText(baseContext, errorCode.getHumanReadableString(), Toast.LENGTH_SHORT).show()
    }

    private fun LoginContract.ErrorCode.getHumanReadableString() = getString(
        when (this) {
            LoginContract.ErrorCode.NO_INTERNET -> R.string.error_no_internet
            LoginContract.ErrorCode.WRONG_PASSWORD -> R.string.error_wrong_password
            else -> R.string.error_unknown
        }
    )

    override fun showEmailValidationError() {
        binding.emailEditText.error = "Не валидный емейл"
    }

    override fun enableLogin(enable: Boolean) {
        binding.loginButton.isEnabled = enable
    }

    override fun showLoginSuccess() {
        Toast.makeText(baseContext, "Ура!", Toast.LENGTH_SHORT).show()
    }

    override fun setEmail(email: String) {
        binding.emailEditText.setText(email)
        binding.emailTextView.setText(email)
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }
}