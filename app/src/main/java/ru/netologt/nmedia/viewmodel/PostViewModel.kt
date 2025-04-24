package ru.netologt.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netologt.nmedia.dto.Post
import ru.netologt.nmedia.repository.PostRepository
import ru.netologt.nmedia.repository.PostRepositoryFilesimpl


private val empty = Post(
    0,
    "",
    "",
    "",
    false,
    false,
    null,
    0,
    0,
    0
)

class PostViewModel (application: Application) : AndroidViewModel(application) {  //created
    private val repository: PostRepository = PostRepositoryFilesimpl(application) // created
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
    fun clearEdited() {
        edited.value  = empty
    }
    fun editContent(post: Post) {
        edited.value?.let {
            edited.value = post
        }
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