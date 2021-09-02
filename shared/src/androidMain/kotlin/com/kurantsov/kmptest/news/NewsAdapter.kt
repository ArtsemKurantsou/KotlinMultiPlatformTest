package com.kurantsov.kmptest.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kurantsov.domain.entity.NewsItem
import com.kurantsov.kmptest.R
import com.kurantsov.kmptest.databinding.ItemNewsBinding
import com.kurantsov.kmptest.utils.toDisplayFormat

class NewsAdapter(
    private val items: List<NewsItem>,
    private val onItemClick: (NewsItem) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(ItemNewsBinding.inflate(inflater, parent, false)).apply {
            itemView.setOnClickListener {
                onItemClick.invoke(items[bindingAdapterPosition])
            }
        }
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class NewsViewHolder(
        private val binding: ItemNewsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewsItem) {
            with(binding) {
                Glide.with(ivImage)
                    .clear(ivImage)
                if (item.imageUrl != null) {
                    Glide.with(ivImage)
                        .load(item.imageUrl)
                        .placeholder(R.drawable.ic_baseline_image_24)
                        .dontTransform()
                        .into(ivImage)
                } else {
                    ivImage.setImageResource(R.drawable.ic_baseline_broken_image_24)
                }
                tvTitle.text = item.title
                tvSource.text = item.source.name
                tvDate.text = item.publishDate.toDisplayFormat()
            }
        }
    }
}