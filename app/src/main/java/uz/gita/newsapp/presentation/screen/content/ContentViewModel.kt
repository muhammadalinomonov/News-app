package uz.gita.newsapp.presentation.screen.content

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.newsapp.navigation.AppNavigator
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(private val navigator: AppNavigator) : ViewModel() {
    fun back() {
        viewModelScope.launch {
            navigator.back()
        }
    }
}