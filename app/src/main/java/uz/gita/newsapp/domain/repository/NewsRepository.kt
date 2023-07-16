package uz.gita.newsapp.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.newsapp.data.source.remote.response.NewsResponse
import uz.gita.newsapp.data.source.remote.response.Article

interface NewsRepository {
    fun loadAllNews(theme: String?, sortBy: String?): Flow<Result<NewsResponse>>
    fun loadTopNews(source: String): Flow<Result<NewsResponse>>
    fun loadCountryNews(cityName: String): Flow<Result<NewsResponse>>
    fun loadNewsBySearch(search: String, _sortBy: String?): Flow<Result<List<Article>>>
}