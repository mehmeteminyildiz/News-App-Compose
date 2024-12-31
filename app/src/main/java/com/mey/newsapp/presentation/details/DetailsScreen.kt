package com.mey.newsapp.presentation.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mey.newsapp.R
import com.mey.newsapp.domain.model.Articles
import com.mey.newsapp.presentation.Dimens
import com.mey.newsapp.presentation.Dimens.ArticleImageHeight
import com.mey.newsapp.presentation.Dimens.MediumPadding1
import com.mey.newsapp.presentation.details.components.DetailsTopBar

@Composable
fun DetailsScreen(
    articles: Articles,
    event: (DetailsEvent) -> Unit,
    navigateUp: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        DetailsTopBar(
            onBrowsingClick = {
                Intent(Intent.ACTION_VIEW).also {
                    it.data = Uri.parse(articles.url)
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            onShareClick = {
                Intent(Intent.ACTION_SEND).also {
                    it.putExtra(Intent.EXTRA_TEXT, articles.url)
                    it.type = "text/plain"
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            onBookMarkClick = {
                event(DetailsEvent.UpsertDeleteArticle(article = articles))
            },
            onBackClick = {
                navigateUp()
            }

        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                start = Dimens.MediumPadding1,
                end = Dimens.MediumPadding1,
                top = Dimens.MediumPadding1
            )
        ) {
            item {
                AsyncImage(
                    model = ImageRequest.Builder(context = context).data(articles.urlToImage).build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(ArticleImageHeight)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(MediumPadding1))
                Text(
                    text = articles.title ?: "",
                    style = MaterialTheme.typography.displaySmall,
                    color = colorResource(R.color.text_title)
                )
                Text(
                    text = articles.content ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(R.color.body)
                )
            }
        }
    }
}
