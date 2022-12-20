package com.example.mybank.ui.signupverify

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mybank.R
import com.example.mybank.databinding.VerifyLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpVerify: Fragment(R.layout.verify_login) {
    private val viewBinding by viewBinding(VerifyLoginBinding::bind)
    private val viewModel: SignUpVerifyViewModel by viewModels()
    private val args by navArgs<SignUpVerifyArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            var timer = 59
            var zero = ""

            (1 .. 59).forEach { _ ->
                zero = if (timer < 10) "0" else ""
                delay(1000)
                timer--
                viewBinding.timerTextview.text = "0:$zero$timer"
            }
        }
        val phoneNumber = args.phoneNumber
        viewBinding.phoneNumber.text = resources.getString(R.string.verify_code,phoneNumber)

//        val view: SmsConfirmationView = viewBinding.smsCodeView.findViewById(R.id.sms_code_view)
//        view.onChangeListener = SmsConfirmationView.OnChangeListener { code, isComplete ->
//
//        }

        viewBinding.verifyButton.setOnClickListener {
            val code = viewBinding.smsCodeView.enteredCode
            args.token?.let { it1 -> viewModel.signUpVerify(it1,code) }
        }

        viewBinding.mistakeTv.text = viewModel.mistakeTv.toString()
        viewModel.mistakeTv.onEach {
            viewBinding.mistakeTv.text = it
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}