package ru.netologt.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netologt.nmedia.dto.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareById(id: Long)
}
