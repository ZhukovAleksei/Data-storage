package ru.netologt.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netologt.nmedia.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {

    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                countLikes.text = formatNumber(post.countLikes.toInt())
                countShare.text = formatNumber(post.countShare.toInt())

                like.setImageResource(R.drawable.baseline_favorite_border_24)
                like.setImageResource(
                    if(post.likedByMe){
                        R.drawable.ic_liked_24
                    }else{
                        R.drawable.baseline_favorite_border_24
                    }
                )
            }
        }
        binding.like.setOnClickListener {
            viewModel.like()
        }
        binding.toShare.setOnClickListener {
            viewModel.toShare()
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

