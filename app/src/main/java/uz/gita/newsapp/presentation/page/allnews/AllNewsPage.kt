package uz.gita.newsapp.presentation.page.allnews

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.valentinilk.shimmer.shimmer
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.newsapp.ui.component.ArticleItem
import uz.gita.newsapp.ui.component.CustomSearchView

class AllNewsPage : Tab, AndroidScreen() {
    override val options: TabOptions
        @Composable
        get() {
            val title = "All news"
            val icon = rememberVectorPainter(Icons.Default.Home)

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
        val viewModel: AllNewsContact.ViewModel = getViewModel<AllNewsViewModel>()
        val uiState = viewModel.collectAsState()

        val context = LocalContext.current

        viewModel.collectSideEffect { sideEffect ->
            when (sideEffect) {
                is AllNewsContact.SideEffect.HasError -> {
                    Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        AllNewsContent(uiState, viewModel::onEventDispatcher)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AllNewsContent(
        uiState: State<AllNewsContact.UiState>,
        onEventDispatcher: (AllNewsContact.Intent) -> Unit
    ) {
        var search by remember { mutableStateOf("") }

        onEventDispatcher(AllNewsContact.Intent.LoadNews(search))

        when (uiState.value) {
            is AllNewsContact.UiState.Loading -> {
                shimmer()
            }

            is AllNewsContact.UiState.NewsData -> {
                LazyColumn {
                    item {
                        CustomSearchView(search = search, onValueChange = {
                            search = it
                        })
                    }
                    items((uiState.value as AllNewsContact.UiState.NewsData).list) {
                        ArticleItem(article = it) { article ->
                            onEventDispatcher.invoke(AllNewsContact.Intent.OpenDetails(article))
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun shimmer(){
        LazyColumn{
            items(5){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)

                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .background(Color(0xFFB8B6B6))
                        .clip(RoundedCornerShape(28.dp))
                        .shimmer(),

                    ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .padding(8.dp)
                            .background(Color(0xFFD3C9C9))
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .padding(8.dp)
                            .background(Color(0xFFD3C9C9))
                    )

                }
            }
        }
    }
}