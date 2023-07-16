package uz.gita.newsapp.presentation.page.saved

import uz.gita.newsapp.data.source.remote.response.Article
import uz.gita.newsapp.navigation.AppNavigator
import javax.inject.Inject

class SavedDirection @Inject constructor(
    private val appNavigator: AppNavigator
) : SaveContract.Direction {
    override suspend fun detailScreen(article: Article) {
        appNavigator.navigateTo(RoomDetail(article))
    }
}