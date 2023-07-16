package uz.gita.newsapp.data.source.remote.response

import uz.gita.newsapp.data.source.remote.response.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)