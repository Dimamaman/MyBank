package com.example.mybank.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mybank.R
import com.example.mybank.databinding.FragmentHomeBinding
import com.example.mybank.ui.adapter.HomeFragmentAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val homeFragmentViewModel: HomeFragmentViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentHomeBinding::bind)
    private val homeAdapter = HomeFragmentAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        homeFragmentViewModel
        homeFragmentViewModel.getAllCards.onEach {
            homeAdapter.list = it
            Log.d("TTT",it.size.toString())
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        initRec()

        homeFragmentViewModel.getAllBalance
            .onEach {
                viewBinding.totalBalance.text = it.toString()
                if (it == 0) {
                    viewBinding.logo.visibility = View.VISIBLE
                }
            }
            .launchIn(lifecycleScope)

        viewBinding.addCard.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddCard())
        }
    }

    private fun initRec() {
        viewBinding.apply {
            recyclerview.adapter = homeAdapter
            recyclerview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }
}