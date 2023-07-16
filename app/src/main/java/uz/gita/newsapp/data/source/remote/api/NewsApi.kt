package uz.gita.newsapp.data.source.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.gita.newsapp.data.source.remote.response.NewsResponse

interface NewsApi {

    @GET("v2/everything")
    suspend fun getAllNews(
        @Query("q") theme: String,
        @Query("from") from: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") key: String,
        @Query("pageSize") page: Int = 100
    ): Response<NewsResponse>

    @GET("v2/top-headlines")
    suspend fun getTopNews(
        @Query("sources") sources: String,
        @Query("apiKey") key: String,
        @Query("pageSize") page: Int = 100
    ): Response<NewsResponse>

    @GET("v2/top-headlines")
    suspend fun getCountryNews(
        @Query("country") country: String,
        @Query("apiKey") key: String,
        @Query("pageSize") page: Int = 100
    ): Response<NewsResponse>
}