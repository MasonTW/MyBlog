package com.mx.blog.entity

import com.mx.blog.DTO.CommentDTO
import javax.persistence.*

@Entity
class Comment(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var commentId: Long = -1,
    var articleId: Long,
    var commentTime: String,
    var userId: Long,
    var commentStar: Int = 0,
    var commentContent: String,
){
    companion object {
        fun toCommentDTO(comment: Comment): CommentDTO {
            return CommentDTO(
                userId = comment.userId,
                commentStar = comment.commentStar,
                commentContent = comment.commentContent
            )
        }
    }
}
