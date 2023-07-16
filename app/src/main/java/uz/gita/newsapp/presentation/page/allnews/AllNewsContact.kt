package uz.gita.newsapp.presentation.page.allnews

import org.orbitmvi.orbit.ContainerHost
import uz.gita.newsapp.data.source.remote.response.Article

interface AllNewsContact {
    sealed interface Intent {
        data class LoadNews(val search: String) : Intent
        data class OpenDetails(val article: Article) : Intent
    }

    sealed interface UiState {
        object Loading : UiState
        data class NewsData(val list: List<Article>) : UiState
    }

    sealed interface SideEffect {
        data class HasError(val message: String) : SideEffect
    }

    interface Direction {
        suspend fun opnDetailsScreen(article: Article)
    }

    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }
}