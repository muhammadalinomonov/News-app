package uz.gita.newsapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

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