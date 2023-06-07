package com.example.movie.ui.screen.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movie.R
import com.example.movie.ui.theme.MovieTheme


@Composable
fun AboutScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(start = 18.dp, end = 18.dp, top = 46.dp, bottom = 36.dp)
                .weight(1f)
                .align(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(R.drawable.hilalhaha),
                contentDescription = stringResource(id = R.string.namaprofile),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)

            )
            Text(
                text = stringResource(id = R.string.namaprofile),
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(id = R.string.email),
                textAlign = TextAlign.Center
            )
            Button(
                onClick = {} ,
                modifier = modifier
                    .width(180.dp)
                    .height(80.dp)
                    .padding(top=36.dp)
            ) {
                Text(
                    text = "Logout",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

        }

    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    MovieTheme() {
        AboutScreen(
            navigateBack = {}
        )
    }
}