package com.mx.blog.controller

import com.mx.blog.DTO.CommentDTO
import com.mx.blog.entity.Comment
import com.mx.blog.service.CommentService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession

@Controller
class CommentController(
    private val commentService: CommentService,
){
    @PostMapping("/article/{articleId}/comment")
    @ResponseBody
    fun addComment(@RequestParam comment: String, @PathVariable articleId: Long, session: HttpSession): Comment  {
         return commentService.addComment(comment, articleId, session)
    }

    @PostMapping("/comment/{commentId}")
    @ResponseBody
    fun deleteComment(@PathVariable commentId: Long) {
        commentService.deleteComment(commentId)
    }

    @PostMapping("/article/comments/{articleId}")
    fun getArticleComments(@PathVariable articleId: Long): List<CommentDTO> {
        return commentService.getArticleComments(articleId)
    }


}