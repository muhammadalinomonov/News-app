package uz.gita.newsapp.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.newsapp.data.source.remote.response.Article

interface AppRepository {
    fun add(newsData: Article)
    fun delete(newsData: Article)
    fun update(newsData: Article)
    fun retrieveAllNews(): Flow<List<Article>>

    fun checkNews(url: String): Article?

    fun getNews(contactId: Long): Article
}