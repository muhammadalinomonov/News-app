package uz.gita.newsapp.presentation.screen.details

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.newsapp.R
import uz.gita.newsapp.data.source.remote.response.Article
import uz.gita.newsapp.navigation.AppScreen
import uz.gita.newsapp.utils.parseDate

class DetailsScreen(private val article: Article) : AppScreen() {
    @Composable
    override fun Content() {
        val viewModel: DetailsContract.ViewModel = getViewModel<DetailsViewModel>()
        val uiState = viewModel.collectAsState().value
        DetailScreenContent(article, uiState, viewModel::onEventDispatcher)
    }


    @Composable
    private fun DetailScreenContent(
        article: Article,
        uiState: DetailsContract.UIState,
        onEventDispatcher: (DetailsContract.Intent) -> Unit
    ) {
        var save by remember { mutableStateOf(true) }

        onEventDispatcher.invoke(DetailsContract.Intent.CheckNews(article))

        when (uiState) {
            is DetailsContract.UIState.Loading -> {
                onEventDispatcher.invoke(DetailsContract.Intent.CheckNews(article))
            }

            is DetailsContract.UIState.CheckNews -> {
                save = uiState.state
            }
        }

        val state = rememberScrollState()
        LaunchedEffect(Unit) { state.animateScrollTo(100) }

        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(12.dp)
                .verticalScroll(state)
        ) {
            Box(modifier = Modifier) {

                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp)),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(article.urlToImage)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.no_pictures),
                    error = painterResource(id = R.drawable.noimage),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
                IconButton(
                    modifier = Modifier
                        .size(50.dp)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(50)),
                    onClick = { onEventDispatcher(DetailsContract.Intent.Back) }) {
                    Image(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier.padding(vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                article.title?.let {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = it,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
                Image(
                    modifier = Modifier
                        .size(20.dp)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = rememberRipple(
                                bounded = false,
                                radius = 50.dp,
                                color = Color.Gray
                            ),
                            onClick = {
                                Log.d("HHH", "onClick Save -> $save")
                                if (save) {
                                    onEventDispatcher(DetailsContract.Intent.Save(article))
                                } else {
                                    onEventDispatcher(DetailsContract.Intent.Remove(article))
                                }
                            }
                        ),
                    painter = if (save) painterResource(id = R.drawable.bookmark) else painterResource(
                        id = R.drawable.bookmark_border
                    ),
                    contentDescription = ""

                )
            }
            Spacer(modifier = Modifier.height(5.dp))

            article.description?.let {
                Text(
                    text = it
                )
            }
            Spacer(modifier = Modifier.height(5.dp))

            val index = article.content?.indexOf("...", 0)
            Text(text = article.content!!.substring(0, index!! + 3), fontSize = 28.sp)

            Spacer(modifier = Modifier.height(5.dp))
            Row(modifier = Modifier.padding(5.dp)) {
                Text(text = "Source: ")
                article.source?.let { Text(text = it.name, modifier = Modifier.weight(1f)) }
                article.publishedAt?.let { Text(text = it.parseDate()) }
            }

            Spacer(
                modifier = Modifier
                    .height(0.dp)
                    .weight(1f)
            )
            Image(
                painter = painterResource(id = R.drawable.web),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.End)
                    .size(50.dp)
                    .clip(CircleShape)
                    .clickable {
                        onEventDispatcher(DetailsContract.Intent.OpenContent(article.url!!))
                    }
            )
            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )
        }
    }
}