package ru.netologt.nmedia

import androidx.lifecycle.ViewModel
import ru.netologt.nmedia.repository.PostRepository
import ru.netologt.nmedia.repository.PostRepositoryInMemoryImpl

class PostViewModel: ViewModel() {

    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
}