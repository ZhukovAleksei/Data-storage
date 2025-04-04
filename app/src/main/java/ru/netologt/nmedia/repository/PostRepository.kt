package ru.netologt.nmedia.repository

import androidx.lifecycle.LiveData
import dto.Post

interface PostRepository {
    fun get(): LiveData<Post>
    fun like()
    fun toShare()
}