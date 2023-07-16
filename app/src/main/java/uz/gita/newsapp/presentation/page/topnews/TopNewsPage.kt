package uz.gita.newsapp.presentation.page.topnews

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
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

object TopNewsPage : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Top news"
            val icon = rememberVectorPainter(Icons.Default.Warning)

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
        val viewModel: TopNewsPageContract.ViewModel = getViewModel<TopNewsViewModel>()
        val uiState = viewModel.collectAsState().value
        val context = LocalContext.current

        viewModel.collectSideEffect {
            when (it) {
                is TopNewsPageContract.SideEffect.HasError -> {
                    Toast.makeText(context, it.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }

        TopNewsPageContent(uiState, viewModel::onEventDispatcher)
    }
    @Composable
    fun TopNewsPageContent(
        uiState: TopNewsPageContract.UIState,
        onEventDispatcher: (TopNewsPageContract.Intent) -> Unit
    ) {
        onEventDispatcher.invoke(TopNewsPageContract.Intent.LoadNews)

        when (uiState) {
            is TopNewsPageContract.UIState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(strokeWidth = 5.dp)
                }
            }

            is TopNewsPageContract.UIState.NewsData -> {
                LazyColumn {
                    items(uiState.topNews) {
                        ArticleItem(article = it) { article ->
                            onEventDispatcher.invoke(TopNewsPageContract.Intent.DetailScreen(article))
                        }
                    }
                }
            }
        }
    }
}
