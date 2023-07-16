package uz.gita.newsapp.domain.repository.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.newsapp.data.source.remote.response.NewsResponse
import uz.gita.newsapp.data.source.remote.api.NewsApi
import uz.gita.newsapp.data.source.remote.response.Article
import uz.gita.newsapp.domain.repository.NewsRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsApi: NewsApi) : NewsRepository {

    companion object {
        private const val apiKey = "7710952f375d4540980fa4669caade08"
    }

    override fun loadAllNews(theme: String?, sortBy: String?): Flow<Result<NewsResponse>> =
        flow {
            val response = newsApi.getAllNews(
                theme ?: "Uzbekistan",
                getThreeDaysAgo(),
                sortBy ?: "popularity",
                apiKey
            )

            when (response.code()) {
                in 200..300 -> {
                    emit(Result.success(response.body() as NewsResponse))
                }

                else -> {
                    emit(Result.failure(Exception(response.errorBody().toString())))
                }
            }

        }.catch { emit(Result.failure(Exception("Server bilan aloqa mavjud emas, internetizingizni tekshirib ko'ring"))) }
            .flowOn(Dispatchers.IO)


    override fun loadTopNews(source: String): Flow<Result<NewsResponse>> = flow {
        val response = newsApi.getTopNews(source, apiKey)

        when (response.code()) {
            in 200..300 -> {
                emit(Result.success(response.body()!!))
            }

            else -> {
                emit(Result.failure(Exception(response.errorBody().toString())))
            }
        }
    }.catch { emit(Result.failure(Exception("Server bilan aloqa mavjud emas, internetizingizni tekshirib ko'ring"))) }
        .flowOn(Dispatchers.IO)

    override fun loadCountryNews(cityName: String): Flow<Result<NewsResponse>> = flow {
        val response = newsApi.getCountryNews(cityName, apiKey)

        //us, ru, nz, gb
        when (response.code()) {
            in 200..300 -> {
                emit(Result.success(response.body()!!))
            }

            else -> {
                emit(Result.failure(Exception(response.errorBody().toString())))
            }
        }
    }.catch { emit(Result.failure(Exception("Server bilan aloqa mavjud emas, internetizingizni tekshirib ko'ring"))) }
        .flowOn(Dispatchers.IO)

    override fun loadNewsBySearch(search: String, _sortBy: String?): Flow<Result<List<Article>>> =
        flow {
            val theme = "All"
            val from = getThreeDaysAgo()
            val sortBy = _sortBy ?: "popularity"

            val response = newsApi.getAllNews(theme, from, sortBy, apiKey)
            when (response.code()) {
                in 200..300 -> {
                    val list = ArrayList<Article>()
                    response.body()?.articles?.forEach {
                        if (it.title!!.contains(
                                search,
                                ignoreCase = true
                            ) || it.description!!.contains(
                                search,
                                ignoreCase = true
                            ) || it.content!!.contains(search, ignoreCase = true)
                        ) {
                            list.add(it)
                        }
                    }
                    emit(Result.success(list))
                }

                else -> {
                    emit(Result.failure(Exception((response.errorBody().toString()))))
                }
            }
        }.catch { emit(Result.failure(Exception("Server bilan aloqa mavjud emas, internetizingizni tekshirib ko'ring"))) }
            .flowOn(Dispatchers.IO)

    private fun getThreeDaysAgo(): String {

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -15)

        val threeDaysAgo = calendar.time
        return dateFormat.format(threeDaysAgo)
    }
}