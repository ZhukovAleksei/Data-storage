package ru.netologt.nmedia.dto


data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val likedByMe: Boolean,
    val repostByMe: Boolean,
    val video: String? = null,
    val countLikes: Long,
    val countShare: Long,
    val countView: Long
)


