package uz.gita.newsapp.presentation.page.contrynews

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.newsapp.ui.component.ArticleItem

class CountryNewsPage : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "USA news"
            val icon = rememberVectorPainter(Icons.Default.AccountCircle)
            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel: CountryNewsContract.ViewModel = getViewModel<CountryNewsViewModel>()
        val uiState = viewModel.collectAsState()
        val context = LocalContext.current
        viewModel.collectSideEffect {
            when (it) {
                is CountryNewsContract.SideEffect.HasError -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        CountryNewsPageContent(uiState, viewModel::onEventDispatcher)
    }

    @Composable
    fun CountryNewsPageContent(
        uiState: State<CountryNewsContract.UiState>,
        onEventDispatcher: (CountryNewsContract.Intent) -> Unit
    ) {
        onEventDispatcher.invoke(CountryNewsContract.Intent.LoadNews)

        when (uiState.value) {
            is CountryNewsContract.UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(strokeWidth = 5.dp)
                }
            }

            is CountryNewsContract.UiState.NewsData -> {
                LazyColumn {
                    items((uiState.value as CountryNewsContract.UiState.NewsData).news) {
                        ArticleItem(article = it) { article ->
                            onEventDispatcher.invoke(
                                CountryNewsContract.Intent.OpenDetailsScreen(
                                    article
                                )
                            )
                        }
                    }

                }
            }
        }
    }
}