package uz.gita.newsapp.presentation.page.saved

import org.orbitmvi.orbit.ContainerHost
import uz.gita.newsapp.data.source.remote.response.Article

interface SaveContract {
    sealed interface Intent {
        object Loading : Intent
        data class GoDetails(val article: Article) : Intent
        data class DeleteNews(val article: Article) : Intent
    }

    sealed interface UISate {
        data class Loading(val loading: Boolean = false) : UISate
        data class AllNewsFromLocal(val articles: List<Article>) : UISate
    }

    sealed interface SideEffect {
        data class HasError(val errorMessage: String) : SideEffect
    }

    interface ViewModel : ContainerHost<UISate, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    interface Direction {
        suspend fun detailScreen(article: Article)
    }
}