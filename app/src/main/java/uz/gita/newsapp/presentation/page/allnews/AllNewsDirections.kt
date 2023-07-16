package uz.gita.newsapp.presentation.page.allnews

import uz.gita.newsapp.data.source.remote.response.Article
import uz.gita.newsapp.navigation.AppNavigator
import uz.gita.newsapp.presentation.screen.details.DetailsScreen
import javax.inject.Inject

class AllNewsDirections @Inject constructor(private val navigator: AppNavigator) :
    AllNewsContact.Direction {
    override suspend fun opnDetailsScreen(article: Article) {
        navigator.navigateTo(DetailsScreen(article))
    }
}