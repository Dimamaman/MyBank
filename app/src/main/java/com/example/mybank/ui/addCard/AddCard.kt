package com.example.mybank.ui.addCard

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mybank.R
import com.example.mybank.data.remote.request.AddCardRequest
import com.example.mybank.databinding.CardAddBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class AddCard : Fragment(R.layout.card_add) {
    private val viewBinding by viewBinding(CardAddBinding::bind)
    private val viewModel: AddCardViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.sendName(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        viewBinding.pan.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.sendNumber(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        viewBinding.expiredYear.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.sendData(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        viewModel.enabledButton.onEach {
            viewBinding.submitBtn.isEnabled = it
        }.launchIn(lifecycleScope)

        viewBinding.submitBtn.setOnClickListener {
            viewBinding.apply {
                val card = AddCardRequest(
                    pan.text.toString(),
                    expiredYear.text.toString(),
                    "09",
                    name.text.toString()
                )
                viewModel.addCard(card)
            }
        }
        viewBinding.backBtn.setOnClickListener {
            findNavController().navigate(AddCardDirections.actionAddCardToHomeFragment())
        }
    }
}