package com.mx.blog.entity

import com.mx.blog.DTO.conllection.CollectionCreateDTO
import com.mx.blog.DTO.conllection.CollectionsDTO
import javax.persistence.*

@Entity
class Collection(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var collectionId: Long = -1,
    var userId: Long,
    var name: String,
    var articleNum: Long = 0,
    @OneToMany(targetEntity = ArticleCollection::class, cascade = [CascadeType.ALL], mappedBy = "collectionId")
    var collectionArticles: List<ArticleCollection> = mutableListOf(),
){
    companion object {
        fun toCollection(collectionCreateDTO: CollectionCreateDTO): Collection {
            return Collection(userId = collectionCreateDTO.userId, name = collectionCreateDTO.name)
        }

        fun toCollectionsDTO(collection: Collection): CollectionsDTO {
            return CollectionsDTO(
                collectionId = collection.collectionId,
                name = collection.name,
                articleNum = collection.collectionArticles.size.toLong()
            )
        }
    }
}
