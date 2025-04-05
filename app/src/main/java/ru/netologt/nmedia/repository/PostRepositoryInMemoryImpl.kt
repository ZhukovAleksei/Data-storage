package ru.netologt.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netologt.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {

    private var post = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разметке, аналитике и управлению. Мы растем сами и помогаем расти студентам: от новичков до ууверенных профессионалов. Но самое важноеостается с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен -> http://netolo.gy/fyb",
        published = "21 мая в 18:36",
        likedByMe = false,

        countLikes = 999,
        countShare = 999,
        repostByMe = false

    )

    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data

    override fun like() {
        post = post.copy(likedByMe = !post.likedByMe)
        post = if (post.likedByMe) post.copy(countLikes = post.countLikes + 1) else post.copy(
            countLikes = post.countLikes - 1
        )
        data.value = post
    }

    override fun toShare() {
        post = post.copy(repostByMe = !post.repostByMe)
        post = if (post.repostByMe) post.copy(countShare = post.countShare + 1) else post.copy(
            countShare = post.countShare - 1
        )
        data.value = post

    }
}