package com.mx.blog.service

import com.mx.blog.DTO.CommentDTO
import com.mx.blog.entity.Comment
import com.mx.blog.repository.CommentRepository
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository: CommentRepository,
) {
    fun addComment(comment: String, articleId: Long, userId: Long): Comment {
        val newComment = Comment(
            articleId = articleId,
            commentTime = System.currentTimeMillis().toString(),
            commentContent = comment,
            userId = userId
        )
        return commentRepository.save(newComment)
    }

    fun deleteComment(commentId: Long): Boolean {
        return try {
            commentRepository.deleteById(commentId)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    fun getArticleComments(articleId: Long): List<CommentDTO> {
        return commentRepository.findAllByArticleId(articleId).map { Comment.toCommentDTO(it) }
    }



}