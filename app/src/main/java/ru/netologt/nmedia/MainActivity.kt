package ru.netologt.nmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netologt.nmedia.databinding.ActivityMainBinding
import ru.netologt.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разметке, аналитике и управлению. Мы растем сами и помогаем расти студентам: от новичков до ууверенных профессионалов. Но самое важноеостается с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен -> http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false,

            countLikes = 999,
            countShare = 999,
            repostByMe = false

        )


        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            countLikes.text = post.countLikes.toString()
            countShare.text = post.countShare.toString()

            like.setOnClickListener {
                post.likedByMe = !post.likedByMe
                if (post.likedByMe) post.countLikes++ else post.countLikes--
                like.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.baseline_favorite_border_24)
                countLikes.text = formatNumber(post.countLikes.toInt())
            }

            toShare.setOnClickListener {
                post.repostByMe = !post.repostByMe
                if (post.repostByMe) post.countShare++ else post.countShare--
                countShare.text = formatNumber(post.countShare.toInt())
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
}