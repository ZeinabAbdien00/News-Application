package com.example.newsapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapplication.R
import com.example.newsapplication.databinding.ItemArticlePreviewBinding
import com.example.newsapplication.models.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(val binding: ItemArticlePreviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding =
            ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

        val article = differ.currentList[position]
        holder.binding.root.apply {

            if (article.urlToImage != null) {
                Glide.with(this).load(article.urlToImage).into(holder.binding.ivArticleImage)
            } else {
                holder.binding.ivArticleImage.setImageResource(R.drawable.img)
            }

            holder.binding.apply {
                if (article.title != null) {
                    tvTitle.text = article.title
                } else {
                    tvTitle.text = "No-Title"
                }

                if (article.description != null) {
                    tvDescription.text = article.description
                } else {
                    tvDescription.text = "No-description"
                }

                tvSource.text = article.source.name
                val splitter = article.publishedAt.split("T")
                tvPublishedAt.text = splitter[0]
            }
            setOnClickListener {
                onItemClickListener?.let { it(article) }
            }

        }
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}