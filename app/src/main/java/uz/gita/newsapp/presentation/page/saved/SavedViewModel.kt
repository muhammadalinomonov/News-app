package uz.gita.newsapp.presentation.page.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.newsapp.domain.repository.AppRepository
import uz.gita.newsapp.presentation.page.saved.SaveContract
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val appUsesCase: AppRepository,
    private val direction: SaveContract.Direction
) : SaveContract.ViewModel, ViewModel() {
    override val container =
        container<SaveContract.UISate, SaveContract.SideEffect>(SaveContract.UISate.Loading())

    override fun onEventDispatcher(intent: SaveContract.Intent) {
        when (intent) {
            is SaveContract.Intent.Loading -> {
                appUsesCase.retrieveAllNews().onEach {
                    intent {
                        reduce {
                            SaveContract.UISate.Loading(true)
                            SaveContract.UISate.AllNewsFromLocal(it)
                        }
                    }
                }.launchIn(viewModelScope)

                SaveContract.UISate.Loading()
            }

            is SaveContract.Intent.GoDetails -> {
                viewModelScope.launch {
                    direction.detailScreen(intent.article)
                }
            }

            is SaveContract.Intent.DeleteNews -> {
                appUsesCase.delete(intent.article)
            }
        }
    }
}