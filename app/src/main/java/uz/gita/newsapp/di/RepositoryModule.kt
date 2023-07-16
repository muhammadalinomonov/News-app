package uz.gita.newsapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.newsapp.domain.repository.AppRepository
import uz.gita.newsapp.domain.repository.NewsRepository
import uz.gita.newsapp.domain.repository.impl.AppRepositoryImpl
import uz.gita.newsapp.domain.repository.impl.NewsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @[Binds Singleton]
    fun getAppRepository(impl: AppRepositoryImpl): AppRepository

    @[Binds Singleton]
    fun getNewsRepository(impl: NewsRepositoryImpl): NewsRepository
}