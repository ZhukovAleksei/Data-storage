package ru.netologt.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dto.Post

class PostRepositoryInMemoryImpl: PostRepository {

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

    override fun get(): LiveData<Post>  = data

    override fun like() {
        post = post.copy(likedByMe = !post.likedByMe)
        if (post.likedByMe) post.countLikes++ else post.countLikes--
        data.value = post
    }
    override fun toShare(){
        post = post.copy(repostByMe = !post.repostByMe)
        if (post.repostByMe) post.countShare++ else post.countShare--
        data.value = post

    }
}