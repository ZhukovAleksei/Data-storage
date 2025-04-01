package ru.netologt.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    var likedByMe: Boolean,

    var countLikes:Long,
    var countShare:Long,
    var repostByMe:Boolean
)
