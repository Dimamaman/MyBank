package com.example.mybank.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mybank.R
import com.example.mybank.data.remote.response.CardResponse
import com.example.mybank.databinding.CardItemBinding

class HomeFragmentAdapter: RecyclerView.Adapter<HomeFragmentAdapter.HomeViewModelViewHolder>() {
    var list: List<CardResponse> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    inner class HomeViewModelViewHolder(val binding: CardItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewModelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        val binding = CardItemBinding.bind(view)
        return HomeViewModelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewModelViewHolder, position: Int) {
        val card = list[position]
        holder.binding.cardNumber.text = card.pan
        holder.binding.year.text = card.expiredYear.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}