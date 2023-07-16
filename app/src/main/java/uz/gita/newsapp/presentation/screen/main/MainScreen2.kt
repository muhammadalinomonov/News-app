package uz.gita.newsapp.presentation.screen.main

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.newsapp.navigation.AppScreen
import uz.gita.newsapp.presentation.page.allnews.AllNewsContact
import uz.gita.newsapp.presentation.page.allnews.AllNewsPage
import uz.gita.newsapp.presentation.page.allnews.AllNewsViewModel
import uz.gita.newsapp.presentation.page.contrynews.CountryNewsContract
import uz.gita.newsapp.presentation.page.contrynews.CountryNewsPage
import uz.gita.newsapp.presentation.page.contrynews.CountryNewsViewModel
import uz.gita.newsapp.presentation.page.saved.SaveContract
import uz.gita.newsapp.presentation.page.saved.Saved
import uz.gita.newsapp.presentation.page.saved.SavedViewModel
import uz.gita.newsapp.presentation.page.topnews.TopNewsPage
import uz.gita.newsapp.presentation.page.topnews.TopNewsPageContract
import uz.gita.newsapp.presentation.page.topnews.TopNewsViewModel

class MainScreen2 : AppScreen() {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        @OptIn(ExperimentalFoundationApi::class)
        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()


        val tabs = listOf(
            TabData(
                title = "All news",
                screen = AllNewsPage()
            ),
            TabData(
                title = "Top news",
                screen = TopNewsPage
            ),
            TabData(
                title = "USA news",
                screen = CountryNewsPage()
            ),
            TabData(
                title = "Saved news",
                screen = Saved
            ),
        )

        Column(modifier = Modifier.fillMaxSize()) {
            TabRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFF03A9F4)),
                selectedTabIndex = pagerState.currentPage,
                containerColor = Color(0xFF03A9F4)
            ) {
                tabs.forEachIndexed { index, item ->
                    Tab(
                        selected = index == pagerState.currentPage,
                        text = { Text(text = item.title, color = Color.White, fontSize = 16.sp) },
                        icon = { },
                        onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    )
                }
            }

            HorizontalPager(
                pageCount = tabs.size,
                state = pagerState
            ) {
                when (it) {
                    0 -> {
                        val viewModel: AllNewsContact.ViewModel = getViewModel<AllNewsViewModel>()
                        val uiState = viewModel.collectAsState()

                        val context = LocalContext.current

                        viewModel.collectSideEffect { sideEffect ->
                            when (sideEffect) {
                                is AllNewsContact.SideEffect.HasError -> {
                                    Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }
                        AllNewsPage().AllNewsContent(uiState, viewModel::onEventDispatcher)
                    }

                    1 -> {
                        val viewModel: TopNewsPageContract.ViewModel =
                            getViewModel<TopNewsViewModel>()
                        val uiState = viewModel.collectAsState().value
                        val context = LocalContext.current

                        viewModel.collectSideEffect {
                            when (it) {
                                is TopNewsPageContract.SideEffect.HasError -> {
                                    Toast.makeText(context, it.errorMessage, Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }

                        TopNewsPage.TopNewsPageContent(uiState, viewModel::onEventDispatcher)
                    }

                    2 -> {
                        val viewModel: CountryNewsContract.ViewModel =
                            getViewModel<CountryNewsViewModel>()
                        val uiState = viewModel.collectAsState()
                        val context = LocalContext.current
                        viewModel.collectSideEffect {
                            when (it) {
                                is CountryNewsContract.SideEffect.HasError -> {
                                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        CountryNewsPage().CountryNewsPageContent(
                            uiState,
                            viewModel::onEventDispatcher
                        )
                    }

                    3 -> {
                        val viewModel: SaveContract.ViewModel = getViewModel<SavedViewModel>()
                        val uiState = viewModel.collectAsState().value
                        val context = LocalContext.current

                        viewModel.collectSideEffect {
                            when (it) {
                                is SaveContract.SideEffect.HasError -> {
                                    Toast.makeText(context, it.errorMessage, Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }

                        Saved.SaveContent(uiState, viewModel::onEventDispatcher)
                    }

                }
            }
        }
    }
}