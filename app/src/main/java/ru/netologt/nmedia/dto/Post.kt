package ru.netologt.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val likedByMe: Boolean,

    val countLikes: Long,
    val countShare: Long,
    val countView: Long,
    val repostByMe: Boolean
)


