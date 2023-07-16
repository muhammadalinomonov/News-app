package uz.gita.newsapp.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import uz.gita.newsapp.data.source.local.entity.ArticleEntity

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(article: ArticleEntity)

    @Query("DELETE FROM news WHERE url = :url")
    fun delete(url: String)

    @Query("SELECT * FROM news WHERE url = :url")
    fun checkNews(url: String): ArticleEntity?

    @Update
    fun update(article: ArticleEntity)

    @Query("Select * FROM news")
    fun retrieveAllContacts(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM news WHERE id = :id")
    fun getContactById(id: Int): ArticleEntity

}