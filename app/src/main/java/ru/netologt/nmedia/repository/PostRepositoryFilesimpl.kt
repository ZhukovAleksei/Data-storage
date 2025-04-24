package ru.netologt.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netologt.nmedia.dto.Post
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PostRepositoryFilesimpl(private val context: Context) : PostRepository {

    private var nextId = 1L;
    private var post = emptyList<Post>()
        set(value) {
            field = value
            data.value = value
            sync()
        }


    private val data = MutableLiveData(post)

    init {
        val file = context.filesDir.resolve(FILENAME)
        if (file.exists()) {
            context.openFileInput(FILENAME).bufferedReader().use {
                post = gson.fromJson(it, type)
                nextId = (post.maxOfOrNull { it.id } ?: 0) + 1
            }
        }
    }

    override fun get(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        post = post.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                countLikes = if (!it.likedByMe) it.countLikes + 1 else it.countLikes - 1
            )
        }
    }

    override fun shareById(id: Long) {

        post = post.map {
            if (it.id != id) it else it.copy(
                repostByMe = !it.repostByMe,
                countShare = if (it.repostByMe) it.countShare - 1 else it.countShare + 1
            )
        }
    }

    override fun removeById(id: Long) {
        post = post.filterNot { it.id == id }
    }

    override fun save(posts: Post) {
        post = if (posts.id == 0L) {
            listOf(posts.copy(id = nextId++, author = "Me", published = "Now")) + post

        } else {
            post.map {
                if (it.id != posts.id) it else it.copy(content = posts.content)
            }
        }
    }

    private fun sync() {
        context.openFileOutput(FILENAME, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(post))
        }
    }

    companion object {
        private const val FILENAME = "posts.json"

        private val gson = Gson()
        private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    }
}