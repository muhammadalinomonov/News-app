package uz.gita.newsapp.data.source.remote.response

import uz.gita.dima.xabarlarqr.data.remote.response.Source
import uz.gita.newsapp.data.source.local.entity.ArticleEntity

data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) {
    fun toEntity() = ArticleEntity(
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