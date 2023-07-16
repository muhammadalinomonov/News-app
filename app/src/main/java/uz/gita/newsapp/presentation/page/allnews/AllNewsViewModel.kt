package uz.gita.newsapp.presentation.page.allnews

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
class AllNewsViewModel @Inject constructor(
    private val repository: NewsRepository,
    private val directions: AllNewsContact.Direction
) : AllNewsContact.ViewModel, ViewModel() {
    override val container =
        container<AllNewsContact.UiState, AllNewsContact.SideEffect>(AllNewsContact.UiState.Loading)

    override fun onEventDispatcher(intent: AllNewsContact.Intent) {
        when (intent) {
            is AllNewsContact.Intent.LoadNews -> {
                repository.loadNewsBySearch(intent.search, null).onEach {
                    it.onSuccess {
                        intent {
                            reduce {
                                AllNewsContact.UiState.NewsData(it)
                            }
                        }
                    }
                    it.onFailure {
                        intent {
                            postSideEffect(AllNewsContact.SideEffect.HasError(it.message!!))
                        }
                    }
                }.launchIn(viewModelScope)
            }

            is AllNewsContact.Intent.OpenDetails -> {
                viewModelScope.launch {
                    directions.opnDetailsScreen(intent.article)
                }
            }
        }
    }
}