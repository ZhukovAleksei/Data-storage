package ru.netologt.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netologt.nmedia.R
import ru.netologt.nmedia.databinding.CardPostBinding
import ru.netologt.nmedia.dto.Post


interface PostEventListener {
    fun onEdit(post: Post)
    fun onRemove(post: Post)
    fun onLike(post: Post)
    fun onShare(post: Post)
}

class PostAdapter(
    private val listener: PostEventListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(
            binding,
            listener,
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val listener: PostEventListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            countViews.text = formatNumber(post.countView.toInt())

            like.apply {
                isChecked = post.likedByMe
                text = formatNumber(post.countLikes.toInt())
            }
            toShare.apply {
                isChecked = post.repostByMe
                text = formatNumber(post.countShare.toInt())
            }

            like.setOnClickListener { listener.onLike(post) }
            toShare.setOnClickListener { listener.onShare(post) }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.post_actions)

                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.remove -> {
                                listener.onRemove(post)
                                true
                                return@setOnMenuItemClickListener true
                            }

                            R.id.edit -> {
                                listener.onEdit(post)
                                true
                                return@setOnMenuItemClickListener true
                            }

                            else -> false
                        }
                    }
                }.show()
            }
        }
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

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: Post, newItem: Post): Any = Unit
}


