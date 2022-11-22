package com.mx.blog.entity

import com.mx.blog.DTO.article.ArticleBasicDTO
import com.mx.blog.DTO.article.ArticleInfoDTO
import javax.persistence.*

@Entity
class Article(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var articleId: Long = -1,
    var articleTitle: String,
    var articleAddTime: String,
    var articleUpdateTime: String,
    var articleContent: String,
    var articleStar: Int = 0,
    var articleLookTimes: Int = 0,
    var articleCollectionNum: Int = 0,
    var articleUserId: Long,
    var isDeleted: Boolean = false,
    @OneToOne(targetEntity = Agreement::class, cascade = [CascadeType.ALL])
    var agreement: Agreement?,
    @OneToMany(targetEntity = Comment::class, cascade = [CascadeType.ALL], mappedBy = "articleId")
    var comments: MutableList<Comment>,
) {
    companion object{
        fun toArticleInfoDTO(article: Article, isAuthor: Boolean = false, isAgreed: Boolean = false): ArticleInfoDTO {
            val agreement = article.agreement
            return ArticleInfoDTO(
                articleTitle = article.articleTitle,
                articleContent = article.articleContent,
                articleStar = article.articleStar,
                articleCollectionNum = article.articleCollectionNum,
                articleLookTimes = article.articleLookTimes,
                commentsNum = article.comments.size.toLong(),
                agreementNum = agreement?.agreementNum ?: 0,
                relationship = ArticleInfoDTO.Relationship(isAuthor, isAgreed)
            )
        }

        fun toArticle(articleBasicDTO: ArticleBasicDTO, userid: Long): Article {
            return Article(
                articleTitle = articleBasicDTO.articleTitle,
                articleContent = articleBasicDTO.articleContent,
                articleAddTime = System.currentTimeMillis().toString(),
                articleUpdateTime = System.currentTimeMillis().toString(),
                articleUserId = userid,
                comments = mutableListOf(),
                agreement = null
            )
        }
    }
}
