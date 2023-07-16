package uz.gita.newsapp.presentation.page.saved

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.dima.xabarlarqr.data.remote.response.Source
import uz.gita.newsapp.R
import uz.gita.newsapp.data.source.remote.response.Article

object Saved : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Saved news"
            val icon = rememberVectorPainter(Icons.Filled.Place)

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
        val viewModel: SaveContract.ViewModel = getViewModel<SavedViewModel>()
        val uiState = viewModel.collectAsState().value
        val context = LocalContext.current

        viewModel.collectSideEffect {
            when (it) {
                is SaveContract.SideEffect.HasError -> {
                    Toast.makeText(context, it.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }

        SaveContent(uiState, viewModel::onEventDispatcher)
    }
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun SaveContent(uiState: SaveContract.UISate, onEventDispatcher: (SaveContract.Intent) -> Unit) {
        onEventDispatcher.invoke(SaveContract.Intent.Loading)

        val showDialog = remember { mutableStateOf(false) }
        val data = remember {
            mutableStateOf(
                Article(
                    "",
                    "",
                    "",
                    "",
                    Source("", ""),
                    "",
                    "",
                    ""
                )
            )
        }
        val swipeRefresh = rememberSwipeRefreshState(isRefreshing = false)

        if (showDialog.value) {
            Log.d("TTT","showDialog -> ${showDialog.value}")
            AlertDialogComponent(onEventDispatcher, data.value, true, showDialog)
        }

        when (uiState) {
            is SaveContract.UISate.Loading -> {
                swipeRefresh.isRefreshing = true
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(strokeWidth = 5.dp)
                }
            }

            is SaveContract.UISate.AllNewsFromLocal -> {
                SwipeRefresh(state = swipeRefresh, onRefresh = {

                    onEventDispatcher(SaveContract.Intent.Loading)
                    uiState.articles
                }) {
                    if (uiState.articles.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(painter = painterResource(id = R.drawable.no_pictures), contentDescription = "")
                        }
                    } else {
                        LazyColumn {
                            Log.d("LLL", uiState.articles.toString())
                            items(uiState.articles) {
                                ArticleItemLocal(article = it, modifier = Modifier.combinedClickable(onClick = {
                                    onEventDispatcher.invoke(SaveContract.Intent.GoDetails(it))
                                } , onLongClick = {
                                    data.value = it
                                    showDialog.value = true
                                }))
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun AlertDialogComponent(
        onEventDispatcher: (SaveContract.Intent) -> Unit,
        data: Article,
        show: Boolean,
        showDialog: MutableState<Boolean>
    ) {
        val context = LocalContext.current
        val openDialog = remember { mutableStateOf(show) }

        if (openDialog.value) {
            AlertDialog(
                properties = DialogProperties(dismissOnClickOutside = false),
                onDismissRequest = { openDialog.value = false },
                title = { Text(text = "Warning", color = Color.White) },
                text = { Text("Do you want to delete ?", color = Color.White) },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onEventDispatcher(SaveContract.Intent.DeleteNews(data))
                            openDialog.value = false
                            showDialog.value = false
                            Toast.makeText(context, "Item deleted", Toast.LENGTH_LONG).show()
                        }
                    ) { Text("Confirm", color = Color.White) }
                },
                dismissButton = {
                    TextButton(onClick = {
                        openDialog.value = false
                        showDialog.value = false
                    })
                    { Text("Dismiss", color = Color.White) }
                },
                containerColor = Color.Gray
            )
        }
    }
}
