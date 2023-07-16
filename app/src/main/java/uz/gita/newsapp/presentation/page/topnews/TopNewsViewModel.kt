package uz.gita.newsapp.presentation.page.topnews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.newsapp.domain.repository.NewsRepository
import javax.inject.Inject

@HiltViewModel
class TopNewsViewModel @Inject constructor(
    private val repository: NewsRepository,
    private val direction: TopNewsPageContract.Direction
) : TopNewsPageContract.ViewModel, ViewModel() {

    override val container = container<TopNewsPageContract.UIState, TopNewsPageContract.SideEffect>(
        TopNewsPageContract.UIState.Loading
    )

    override fun onEventDispatcher(intent: TopNewsPageContract.Intent) {
        when (intent) {
            is TopNewsPageContract.Intent.LoadNews -> {
                repository.loadTopNews("bbc-news").onEach {
                    it.onSuccess {
                        intent {
                            reduce {
                                TopNewsPageContract.UIState.NewsData(it.articles)
                            }
                        }
                    }

                    it.onFailure {
                        intent {
                            postSideEffect(TopNewsPageContract.SideEffect.HasError(it.message!!))
                        }
                    }
                }.launchIn(viewModelScope)
            }

            is TopNewsPageContract.Intent.DetailScreen -> {
                viewModelScope.launch {
                    direction.detailScreen(intent.article)
                }
            }
        }
    }
}