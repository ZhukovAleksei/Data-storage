package ru.netologt.nmedia

import androidx.lifecycle.ViewModel
import ru.netologt.nmedia.repository.PostRepository
import ru.netologt.nmedia.repository.PostRepositoryInMemoryImpl

class PostViewModel: ViewModel() {

    private val repository: PostRepository = PostRepositoryInMemoryImpl()

    val data = repository.get()
    fun like() = repository.like()
    fun toShare() = repository.toShare()
}