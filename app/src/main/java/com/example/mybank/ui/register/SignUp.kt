package com.example.mybank.ui.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mybank.R
import com.example.mybank.databinding.UpSignBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SignUp: Fragment(R.layout.up_sign) {
    private val viewBinding by viewBinding(UpSignBinding::bind)
    private val viewModel: SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.btnRegistration.setOnClickListener {
            val firstname = viewBinding.etFirstName.text.toString()
            val lastname = viewBinding.etLastName.text.toString()
            val dateOfBirth = viewBinding.etDateBirth.text.toString()
            val phone = viewBinding.etPhoneNumber.text.toString()
            val password = viewBinding.etPassword.text.toString()
            val gender = if (viewBinding.genderGroup.checkedRadioButtonId == R.id.male_btn) "0" else "1"

            viewModel.register(firstname, lastname, dateOfBirth, phone, password, gender)
        }

        viewModel.message.onEach {
            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)

        viewModel.loading.onEach { isLoading ->
            viewBinding.progress.isVisible = isLoading
        }.launchIn(lifecycleScope)
    }

}