package uz.gita.newsapp.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.gita.dima.xabarlarqr.data.remote.response.Source
import uz.gita.newsapp.data.source.remote.response.Article

@Entity(tableName = "news")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) {
    fun toArticleData() = Article(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source,
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}