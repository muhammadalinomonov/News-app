package uz.gita.newsapp.presentation.page.contrynews

import uz.gita.newsapp.data.source.remote.response.Article
import uz.gita.newsapp.navigation.AppNavigator
import uz.gita.newsapp.presentation.screen.details.DetailsScreen
import javax.inject.Inject

class CountryNewsDirections @Inject constructor(private val navigator: AppNavigator):CountryNewsContract.Directions {
    override suspend fun openDetailsScreen(article:Article) {
        navigator.navigateTo(DetailsScreen(article))
    }
}