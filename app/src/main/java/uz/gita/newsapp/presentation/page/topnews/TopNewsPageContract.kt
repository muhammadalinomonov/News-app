package uz.gita.newsapp.presentation.page.topnews

import org.orbitmvi.orbit.ContainerHost
import uz.gita.newsapp.data.source.remote.response.Article

interface TopNewsPageContract {

    sealed interface Intent {
        object LoadNews : Intent
        data class DetailScreen(val article: Article) : Intent
    }

    sealed interface UIState {
        object Loading : UIState
        data class NewsData(val topNews: List<Article>) : UIState
    }

    sealed interface SideEffect {
        data class HasError(val errorMessage: String) : SideEffect
    }

    interface Direction {
        suspend fun detailScreen(article: Article)
    }

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }
}