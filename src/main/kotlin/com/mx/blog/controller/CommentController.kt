package com.mx.blog.controller

import com.mx.blog.DTO.CommentDTO
import com.mx.blog.entity.Comment
import com.mx.blog.service.CommentService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class CommentController(
    private val commentService: CommentService,
){
    @PostMapping("/articles/{articleId}/comments")
    @ResponseBody
    fun addComment(@RequestParam comment: String, @PathVariable articleId: Long, @RequestAttribute("userId") userId: Long): Comment  {
         return commentService.addComment(comment, articleId, userId)
    }

    @DeleteMapping("/comments/{commentId}")
    @ResponseBody
    fun deleteComment(@PathVariable commentId: Long): Boolean {
        return commentService.deleteComment(commentId)
    }

    @GetMapping("/articles/{articleId}/comments")
    fun getArticleComments(@PathVariable articleId: Long): List<CommentDTO> {
        return commentService.getArticleComments(articleId)
    }


}