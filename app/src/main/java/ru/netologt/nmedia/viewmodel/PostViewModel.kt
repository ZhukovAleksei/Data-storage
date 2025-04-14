package ru.netologt.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netologt.nmedia.dto.Post
import ru.netologt.nmedia.repository.PostRepositoryInMemory
import ru.netologt.nmedia.repository.PostRepository


private val empty = Post(
    0,
    "",
    "",
    "",
    false,
    0,
    0,
    0,
    false
)

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemory()
    val data = repository.get()

    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)

    val edited = MutableLiveData(empty)

    fun saveContent() {
        edited.value?.let {
            repository.save(it)
            edited.value = empty
        }
    }

    fun editContent(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        edited.value?.let {
            val trimmed = content.trim()
            if (trimmed == it.content) {
                return
            }
            edited.value = it.copy(content = trimmed)
        }
    }
}