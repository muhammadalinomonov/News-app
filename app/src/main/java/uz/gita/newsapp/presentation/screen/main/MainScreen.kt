package uz.gita.newsapp.presentation.screen.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import uz.gita.newsapp.navigation.AppScreen
import uz.gita.newsapp.presentation.page.allnews.AllNewsPage
import uz.gita.newsapp.presentation.page.contrynews.CountryNewsPage
import uz.gita.newsapp.presentation.page.saved.Saved
import uz.gita.newsapp.presentation.page.topnews.TopNewsPage

class MainScreen : AppScreen() {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val tabData = listOf("Technology", "Movies", "Politics", "Education")
        val pagerState = rememberCoroutineScope()


        TabNavigator(AllNewsPage()) { tab ->
            Scaffold(
                content = {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        CurrentTab()
                    }
                },

                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = tab.current.options.title,
                                textAlign = TextAlign.Center
                            )
                        },
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 28.dp)
                    )
                },

                bottomBar = {
                    NavigationBar(modifier = Modifier) {
                        TabNavigationItem(tab = AllNewsPage())
                        TabNavigationItem(tab = CountryNewsPage())
                        TabNavigationItem(tab = TopNewsPage)
                        TabNavigationItem(tab = Saved)
                    }
                }
            )
        }

    }

    @Composable
    private fun RowScope.TabNavigationItem(tab: Tab) {
        val tabNavigator = LocalTabNavigator.current

        NavigationBarItem(
            selected = tabNavigator.current == tab,
            onClick = { tabNavigator.current = tab },
            icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) }
        )
    }
}