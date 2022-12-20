package com.example.mybank.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mybank.R
import com.example.mybank.databinding.ScreenLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LoginScreen : Fragment(R.layout.screen_login) {
    private val viewBinding by viewBinding(ScreenLoginBinding::bind)
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.sendPhone(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}

        })

        viewBinding.password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.sendPassword(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}

        })

        viewModel.enableButton
            .onEach {
                viewBinding.btnLogin.isEnabled = it
            }
            .launchIn(lifecycleScope)

        viewBinding.btnLogin.setOnClickListener {
            viewModel.login(viewBinding.etPhoneNumber.text.toString(),viewBinding.password.text.toString())
        }

        viewBinding.btnRegistration.setOnClickListener {
            viewModel.openRegister()
        }
    }
}