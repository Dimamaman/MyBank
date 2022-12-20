package com.example.mybank.ui.terms

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mybank.R
import com.example.mybank.databinding.ScreenTermsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsScreen : Fragment(R.layout.screen_terms) {
    private val viewBinding by viewBinding(ScreenTermsBinding::bind)
    private val viewModel: TermsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.btnAccept.setOnClickListener { viewModel.accept() }

        viewBinding.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            viewBinding.btnAccept.isEnabled = isChecked
            if (!isChecked) {
                viewBinding.btnAccept.setTextColor(Color.WHITE)
            }
        }
    }
}