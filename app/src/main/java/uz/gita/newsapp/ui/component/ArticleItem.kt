package uz.gita.newsapp.ui.component

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import uz.gita.newsapp.R
import uz.gita.newsapp.data.source.remote.response.Article


@Composable
fun ArticleItem(
    article: Article,
    onClick: (Article) -> Unit,
//    deleteNewsFromLocal: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .wrapContentHeight(Alignment.Top),
        colors = CardDefaults.cardColors(Color(0xFF67C3EC))
    ) {
        var expended by rememberSaveable { mutableStateOf(false) }
        val extraPadding by animateDpAsState(
            if (expended) 48.dp else 0.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )

        Column(modifier = Modifier.padding(4.dp)) {
            Column(modifier = Modifier.padding(4.dp)) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { onClick(article) },
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(article.urlToImage)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.no_pictures),
                    error = painterResource(id = R.drawable.noimage),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
                Column(
                    modifier = Modifier
                        .padding(bottom = extraPadding.coerceAtLeast(0.dp))
                ) {
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        modifier = Modifier.padding(5.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        article.title?.let {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = it,
                                color = Color.Black,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        IconButton(onClick = { expended = !expended }) {
                            Icon(
                                imageVector = if (!expended) Icons.Filled.ArrowDropDown else Icons.Filled.KeyboardArrowUp,
                                contentDescription = null
                            )
                        }
                    }
                    if (expended) {
                        article.description?.let {
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = it
                            )
                        }

                        Row(modifier = Modifier.padding(5.dp)) {
                            Text(text = "Source: ")
                            article.source?.let {
                                Text(
                                    text = it.name,
                                    maxLines = 1,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            article.publishedAt?.let { Text(text = it) }
                        }
                    }
                }
            }
        }
    }
}