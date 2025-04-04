package dto


data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val likedByMe: Boolean,

    var countLikes:Long,
    var countShare:Long,
    var repostByMe:Boolean
)