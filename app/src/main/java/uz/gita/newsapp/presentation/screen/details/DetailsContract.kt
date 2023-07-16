package uz.gita.newsapp.presentation.screen.details

import org.orbitmvi.orbit.ContainerHost
import uz.gita.newsapp.data.source.remote.response.Article

interface DetailsContract {
    sealed interface Intent {
        data class Save(val article: Article) : Intent
        data class Remove(val article: Article) : Intent
        data class CheckNews(val article: Article) : Intent
        object Back:Intent
        data class OpenContent(val url:String):Intent
    }

    sealed interface UIState {
        object Loading : UIState
        data class CheckNews(val state: Boolean) : UIState
    }

    sealed interface SideEffect {
        data class HasError(val errorMessage: String) : SideEffect
    }

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }
}