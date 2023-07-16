package uz.gita.newsapp.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.gita.newsapp.data.source.local.dao.NewsDao
import uz.gita.newsapp.data.source.local.entity.ArticleEntity
import uz.gita.newsapp.utils.DataConverter

@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getContactDao(): NewsDao

}