package ru.netologt.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netologt.nmedia.R
import ru.netologt.nmedia.databinding.CardPostBinding
import ru.netologt.nmedia.dto.Post

typealias OnLikeListener = (Post) -> Unit
typealias OnShareListener = (Post) -> Unit

class PostsAdapter(private val onLikeListener: OnLikeListener, private val onShareListener: OnShareListener) : ListAdapter<Post, PostViewHolder>(PostDiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(view, onLikeListener, onShareListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PostViewHolder(private val binding: CardPostBinding, private val onLikeListener: OnLikeListener, private val onShareListener: OnShareListener) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) = with(binding) {
        author.text = post.author
        published.text = post.published
        content.text = post.content
        countLikes.text = formatNumber(post.countLikes.toInt())
        countShare.text = formatNumber(post.countShare.toInt())

        like.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.baseline_favorite_border_24)

        like.setOnClickListener {
            onLikeListener(post)
        }

        toShare.setOnClickListener {
        onShareListener(post)
        }
    }


    fun formatNumber(number: Int): String {
        return when {
            number < 1000 -> number.toString()
            number < 10_000 -> {
                val decimalPart = (number % 1000) / 100
                val integerPart = number / 1000
                if (decimalPart == 0) {
                    String.format("%dK", integerPart)
                } else {
                    String.format("%d,%dK", integerPart, decimalPart)
                }
            }

            number < 1_000_000 -> {
                val decimalPart = (number % 1000) / 100
                val integerPart = number / 1000
                if (decimalPart == 0) {
                    String.format("%dK", integerPart)
                } else {
                    String.format("%dK", integerPart, decimalPart)
                }
            }

            number < 100_000_000 -> {
                val decimalPart = (number % 1_000_000) / 100_000
                val integerPart = number / 1_000_000
                if (decimalPart == 0) {
                    String.format("%dM", integerPart)
                } else {
                    String.format("%d,%dM", integerPart, decimalPart)
                }
            }

            else -> String.format("%.1fM", number / 1_000_000.0)
        }
    }
}


object PostDiffCallBack : DiffUtil.ItemCallback<Post>() {

    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem
}