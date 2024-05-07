package com.bakhdev.nutaposttest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bakhdev.nutaposttest.databinding.ItemTypeBinding
import com.bakhdev.nutaposttest.domain.model.Type

class ListTypeAdapter(private val onItemClick: (type: Type) -> Unit) :
    ListAdapter<Type, ListTypeAdapter.ViewHolder>(TypeDiffCallback()) {

    class ViewHolder(
        private val binding: ItemTypeBinding,
        private val onItemClick: (type: Type) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun data(type: Type) {
            binding.tvType.text = type.type
            binding.root.setOnClickListener {
                onItemClick(type)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTypeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.data(item)
    }
}

class TypeDiffCallback : DiffUtil.ItemCallback<Type>() {
    override fun areItemsTheSame(oldItem: Type, newItem: Type): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Type, newItem: Type): Boolean = oldItem == newItem
}