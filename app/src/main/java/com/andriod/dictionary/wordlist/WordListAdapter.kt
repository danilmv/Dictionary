package com.andriod.dictionary.wordlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andriod.dictionary.databinding.ItemWordBinding
import com.andriod.dictionary.entity.Word

class WordListAdapter(private val listener: Listener) :
    RecyclerView.Adapter<WordListAdapter.ViewHolder>() {

    private var words = mutableListOf<Word>()

    fun interface Listener {
        fun onClick(word: Word)
    }

    class ViewHolder(parent: ViewGroup, listener: Listener) :
        RecyclerView.ViewHolder(
            ItemWordBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ).root
        ) {
        private var binding: ItemWordBinding = ItemWordBinding.bind(itemView)
        private lateinit var word: Word

        init {
            binding.root.setOnClickListener {
                listener.onClick(word)
            }
        }

        fun onBind(word: Word) {
            this.word = word
            binding.wordTextView.text = word.word
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent, listener)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.onBind(words[position])

    override fun getItemCount() = words.size
    fun setData(users: List<Word>?) {
        users?.let {
            this.words.clear()
            this.words.addAll(users)
            notifyDataSetChanged()
        }
    }
}