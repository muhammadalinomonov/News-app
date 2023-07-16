package uz.gita.newsapp.presentation.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.newsapp.domain.repository.AppRepository
import uz.gita.newsapp.navigation.AppNavigator
import uz.gita.newsapp.presentation.screen.content.ContentScreen
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val navigator: AppNavigator,
    private val repository: AppRepository
) :
    DetailsContract.ViewModel, ViewModel() {
    override val container =
        container<DetailsContract.UIState, DetailsContract.SideEffect>(DetailsContract.UIState.Loading)

    override fun onEventDispatcher(intent: DetailsContract.Intent) {
        when (intent) {
            DetailsContract.Intent.Back -> {
                viewModelScope.launch {
                    navigator.back()
                }
            }

            is DetailsContract.Intent.CheckNews -> {
                if (intent.article.url?.let { repository.checkNews(it) } == null) {
                    intent {
                        reduce {
                            DetailsContract.UIState.CheckNews(true)
                        }
                    }
                } else {
                    intent {
                        reduce {
                            DetailsContract.UIState.CheckNews(false)
                        }
                    }
                }
            }

            is DetailsContract.Intent.Remove -> {
                repository.delete(intent.article)
                intent {
                    reduce {
                        DetailsContract.UIState.CheckNews(true)
                    }
                }
            }

            is DetailsContract.Intent.Save -> {
                repository.add(intent.article)
                intent {
                    reduce {
                        DetailsContract.UIState.CheckNews(false)
                    }
                }
            }

            is DetailsContract.Intent.OpenContent -> {
                viewModelScope.launch {
                    navigator.navigateTo(ContentScreen(intent.url))
                }
            }
        }
    }
}