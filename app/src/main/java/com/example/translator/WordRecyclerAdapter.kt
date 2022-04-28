package com.example.translator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.translator.database.WordEntity
import com.example.translator.databinding.WordRecyclerviewBinding

typealias clickHandler = (WordEntity) ->Unit
class WordRecyclerAdapter(var clickWord: clickHandler): ListAdapter<WordEntity, WordRecyclerAdapter.ItemHolder>(WordDiffCallback) {
    class ItemHolder(val binding:WordRecyclerviewBinding ) : RecyclerView.ViewHolder(binding.root)
    object WordDiffCallback : DiffUtil.ItemCallback<WordEntity>() {
        override fun areItemsTheSame(oldItem: WordEntity, newItem:WordEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WordEntity, newItem:WordEntity): Boolean {
            return oldItem == newItem
            // this is true when you show all variable of doctor     return oldItem == newItem
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding:WordRecyclerviewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.word_recyclerview,
            parent,false
        )
        val view = LayoutInflater.from(parent.context).inflate(R.layout.word_recyclerview, parent, false)
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.binding.liveWord = getItem(position)
        holder.binding.LL.setOnClickListener {
        clickWord?.invoke(getItem(position))
        }
    }
}