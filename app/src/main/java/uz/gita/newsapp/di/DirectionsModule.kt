package uz.gita.newsapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.newsapp.presentation.page.allnews.AllNewsContact
import uz.gita.newsapp.presentation.page.allnews.AllNewsDirections
import uz.gita.newsapp.presentation.page.contrynews.CountryNewsContract
import uz.gita.newsapp.presentation.page.contrynews.CountryNewsDirections
import uz.gita.newsapp.presentation.page.saved.SaveContract
import uz.gita.newsapp.presentation.page.saved.SavedDirection
import uz.gita.newsapp.presentation.page.topnews.TopNewsDirection
import uz.gita.newsapp.presentation.page.topnews.TopNewsPageContract

@Module
@InstallIn(ViewModelComponent::class)
interface DirectionsModule {

    @Binds
    fun bindAllNewsDirections(impl: AllNewsDirections): AllNewsContact.Direction

    @Binds
    fun bindCountryNewsDirections(impl: CountryNewsDirections): CountryNewsContract.Directions

    @Binds
    fun bindTopNewsDirections(impl: TopNewsDirection): TopNewsPageContract.Direction


    @Binds
    fun bindSaveDirection(imp: SavedDirection): SaveContract.Direction
}