package com.dicoding.asclepius.view.history

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.data.dto.ClassificationResult
import com.dicoding.asclepius.databinding.HistoryCardBinding
import com.dicoding.asclepius.helper.ClassificationResultFormatter

class HistoryAdapter(
    private val onClick: (ClassificationResult) -> Unit
) : ListAdapter<ClassificationResult, HistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: HistoryCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: ClassificationResult){
            binding.ivHistory.setImageURI(Uri.parse(result.uri))
            binding.tvHistory.text = ClassificationResultFormatter.invoke(result)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val context = parent.context
        val binding = HistoryCardBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val result = getItem(position)
        holder.bind(result)
        holder.itemView.setOnClickListener{ onClick(result) }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ClassificationResult>() {
            override fun areItemsTheSame(oldItem: ClassificationResult, newItem: ClassificationResult): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ClassificationResult, newItem: ClassificationResult): Boolean {
                return oldItem == newItem
            }
        }
    }
}