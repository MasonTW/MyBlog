package com.mx.blog.controller

import com.mx.blog.DTO.ArticleCreateDTO
import com.mx.blog.entity.Article
import com.mx.blog.entity.Comment
import com.mx.blog.service.ArticleService
import com.mx.blog.service.CommentService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession

@Controller
class CommentController(
    private val commentService: CommentService,
){
    @PostMapping("/article/{articleId}")
    @ResponseBody
    fun addComment(@RequestParam comment: String, @PathVariable articleId: Long, session: HttpSession): Comment  {
         return commentService.addComment(comment, articleId, session)
    }

    @PostMapping("/comment/{commentId}")
    @ResponseBody
    fun deleteComment(@PathVariable commentId: Long) {
        commentService.deleteComment(commentId)
    }

}