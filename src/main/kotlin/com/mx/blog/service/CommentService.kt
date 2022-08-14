package com.mx.blog.service

import com.mx.blog.DTO.CommentDTO
import com.mx.blog.DTO.UserDTO
import com.mx.blog.DTO.UserRegisterDTO
import com.mx.blog.entity.Comment
import com.mx.blog.entity.User
import com.mx.blog.repository.ArticleRepository
import com.mx.blog.repository.CommentRepository
import com.mx.blog.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.servlet.http.HttpSession

@Service
class CommentService(
   private val commentRepository: CommentRepository,
) {
    fun addComment(comment: String, articleId: Long, session: HttpSession): Comment {
        val newComment = Comment(
            articleId = articleId,
            commentTime = System.currentTimeMillis().toString(),
            commentContent = comment,
            userId = session.getAttribute("userId") as Long
        )
       return  commentRepository.save(newComment)
    }

    fun deleteComment(commentId: Long) {
        return commentRepository.deleteById(commentId)
    }

    fun getArticleComments(articleId: Long): List<CommentDTO> {
        return commentRepository.findAllByArticleId(articleId).map { commentDTOMapper(it) }
    }

    private fun commentDTOMapper(comment: Comment): CommentDTO{
        return CommentDTO(userId = comment.userId, commentStar = comment.commentStar, commentContent = comment.commentContent)
    }

}