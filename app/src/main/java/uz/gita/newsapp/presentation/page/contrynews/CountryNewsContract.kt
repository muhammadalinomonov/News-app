package uz.gita.newsapp.presentation.page.contrynews

import org.orbitmvi.orbit.ContainerHost
import uz.gita.newsapp.data.source.remote.response.Article

interface CountryNewsContract {
    sealed interface Intent {
        object LoadNews : Intent
        data class OpenDetailsScreen(val article: Article) : Intent
    }

    sealed interface UiState {
        object Loading : UiState
        data class NewsData(val news: List<Article>) : UiState
    }

    sealed interface SideEffect {
        data class HasError(val message: String) : SideEffect
    }

    interface Directions {
        suspend fun openDetailsScreen(article: Article)
    }

    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

}