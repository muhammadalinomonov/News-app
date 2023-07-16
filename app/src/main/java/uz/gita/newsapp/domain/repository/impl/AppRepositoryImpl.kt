package uz.gita.newsapp.domain.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.gita.newsapp.data.source.local.dao.NewsDao
import uz.gita.newsapp.data.source.remote.response.Article
import uz.gita.newsapp.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(private val dao: NewsDao) : AppRepository {

    override fun add(newsData: Article) {
        dao.add(newsData.toEntity())
    }

    override fun delete(newsData: Article) {
        newsData.url?.let { dao.delete(it) }
    }

    override fun update(newsData: Article) {
        dao.update(newsData.toEntity())
    }

    override fun retrieveAllNews(): Flow<List<Article>> =
        dao.retrieveAllContacts().map { list ->
            list.map {
                it.toArticleData()
            }
        }

    override fun checkNews(url: String): Article? {
        return if (dao.checkNews(url) == null) {
            null
        } else {
            dao.checkNews(url)!!.toArticleData()
        }
    }

    override fun getNews(contactId: Long): Article {
        return dao.getContactById(contactId.toInt()).toArticleData()
    }
}