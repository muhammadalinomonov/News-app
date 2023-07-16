package uz.gita.newsapp.presentation.page.saved

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import uz.gita.newsapp.R
import uz.gita.newsapp.data.source.remote.response.Article
import uz.gita.newsapp.navigation.AppScreen
import uz.gita.newsapp.utils.parseDate

class RoomDetail(val article: Article) : AppScreen() {

    @Composable
    override fun Content() {
        RoomDetailScreenContent(article)
    }
}

@Composable
fun RoomDetailScreenContent(
    article: Article,
) {
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100) }

    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .padding(12.dp)
            .verticalScroll(state)
    ) {
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
                        onClick = { }
                    ),
                painter = painterResource(id = R.drawable.bookmark_border),
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

        Text(text = article.content ?: "", fontSize = 28.sp)

        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier.padding(5.dp)) {
            Text(text = "Source: ")
            article.source?.let { Text(text = it.name, modifier = Modifier.weight(1f)) }
            article.publishedAt?.let { Text(text = it.parseDate()) }
        }
    }
}