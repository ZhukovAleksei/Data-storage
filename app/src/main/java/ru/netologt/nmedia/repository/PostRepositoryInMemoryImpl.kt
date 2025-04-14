package ru.netologt.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netologt.nmedia.dto.Post

class PostRepositoryInMemory : PostRepository {
    private var nextId = 1L;
    private var post = listOf(
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разметке, аналитике и управлению. Мы растем сами и помогаем расти студентам: от новичков до ууверенных профессионалов. Но самое важноеостается с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен -> http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false,

            countLikes = 999,
            countShare = 999,
            countView = 100,
            repostByMe = false

        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий",
            content = "Словом «дедлайн» давно никого не удивить, а «спринт» стал чаще ассоциироваться с работой, чем с бегом. Принесли вам новую порцию словечек, которые можно услышать в общении с коллегами. Рассказывайте: какие фразы из карточек вы ещё не встречали? Или ваш инглиш настолько велл, что новые термины ощущаются как давно знакомые?",
            published = "18 сентября в 18:36",
            likedByMe = false,

            countLikes = 1099,
            countShare = 1099,
            countView = 10_000,
            repostByMe = false

        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий",
            content = "Прислушаться к зову сердца, расширить привычные горизонты и найти дело по душе — за этим приходите на бесплатные занятия!",
            published = "21 мая в 18:36",
            likedByMe = false,

            countLikes = 10_999,
            countShare = 10_999,
            countView = 100_000,
            repostByMe = false

        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий",
            content = "Что общего у банковского приложения, упаковки любимого йогурта и рекламного ролика? За всем этим стоит труд дизайнера.В статье рассказываем об основных видах диджитал-дизайна, важных навыках специалистов этих направлений, их востребованности и зарплатах: netolo.gy/d4yP.",
            published = "21 мая в 18:36",
            likedByMe = false,

            countLikes = 0,
            countShare = 0,
            countView = 10,
            repostByMe = false

        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий",
            content = "Иногда сложно определиться даже со вкусом чипсов или сиропа для кофе — что уж говорить о таком важном шаге, как выбор онлайн-курса. Чтобы вы не потеряли время и деньги — рассказываем, как выбрать подходящий вариант обучения и на какие моменты обратить внимание.",
            published = "21 мая в 18:36",
            likedByMe = false,

            countLikes = 0,
            countShare = 0,
            countView = 10,
            repostByMe = false

        )
    )

    private val data = MutableLiveData(post)

    override fun get(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        post = post.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                countLikes = if (it.likedByMe) it.countLikes - 1 else it.countLikes + 1
            )
        }
        data.value = post
    }

    override fun shareById(id: Long) {

        post = post.map {
            if (it.id != id) it else it.copy(
                repostByMe = !it.repostByMe,
                countShare = if (it.repostByMe) it.countShare - 1 else it.countShare + 1
            )
        }
        data.value = post
    }

    override fun removeById(id: Long) {
        post = post.filterNot { it.id == id }
        data.value = post
    }

    override fun save(posts: Post) {
        post = if (posts.id == 0L) {
            listOf(posts.copy(id = nextId++, author = "Me")) + post
        } else {
            post.map { if (it.id != posts.id) it else it.copy(content = posts.content) }
        }

        data.value = post
    }
}


