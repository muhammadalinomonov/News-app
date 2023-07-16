package uz.gita.newsapp.presentation.page.contrynews

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
class CountryNewsViewModel @Inject constructor(
    private val directions: CountryNewsContract.Directions,
    private val repository: NewsRepository
) : CountryNewsContract.ViewModel, ViewModel() {
    override val container =
        container<CountryNewsContract.UiState, CountryNewsContract.SideEffect>(CountryNewsContract.UiState.Loading)

    override fun onEventDispatcher(intent: CountryNewsContract.Intent) {
        when (intent) {
            CountryNewsContract.Intent.LoadNews -> {
                repository.loadCountryNews("us").onEach {
                    it.onSuccess {
                        intent {
                            reduce {
                                CountryNewsContract.UiState.NewsData(it.articles)
                            }
                        }
                    }
                    it.onFailure {
                        intent { postSideEffect(CountryNewsContract.SideEffect.HasError(it.message!!)) }
                    }
                }.launchIn(viewModelScope)
            }

            is CountryNewsContract.Intent.OpenDetailsScreen -> {
                viewModelScope.launch {
                    directions.openDetailsScreen(intent.article)
                }
            }
        }
    }
}