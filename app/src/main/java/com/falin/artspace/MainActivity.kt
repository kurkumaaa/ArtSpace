package com.falin.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.falin.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ArtSpaceScreen(modifier = Modifier.safeDrawingPadding())
                }
            }
        }
    }
}

private data class Artwork(
    @param:DrawableRes val image: Int,
    @param:StringRes val title: Int,
    @param:StringRes val description: Int,
    @param:StringRes val artist: Int,
    @param:StringRes val year: Int
)

private val artworks = listOf(
    Artwork(R.drawable.mountain_lake, R.string.mountain_lake,
        R.string.mountain_lake_description, R.string.artwork_artist, R.string.artwork_year),
    Artwork(R.drawable.sunset_sea, R.string.sunset_sea,
        R.string.sunset_sea_description, R.string.artwork_artist, R.string.artwork_year),
    Artwork(R.drawable.forest_path, R.string.forest_path,
        R.string.forest_path_description, R.string.artwork_artist, R.string.artwork_year)
)

@Composable
fun ArtSpaceScreen(modifier: Modifier = Modifier) {
    val artwork = artworks[0]
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
    ) {
        ArtworkWall(artwork, Modifier.widthIn(max = 480.dp).fillMaxWidth(), 360.dp)
        ArtworkDescriptor(artwork, 0, Modifier.widthIn(max = 480.dp))
        DisplayController({}, {}, Modifier.widthIn(max = 480.dp))
    }
}

@Composable
private fun ArtworkWall(artwork: Artwork, modifier: Modifier, imageHeight: Dp) {
    Surface(modifier = modifier, shadowElevation = 6.dp, color = MaterialTheme.colorScheme.surface) {
        Image(
            painter = painterResource(artwork.image),
            contentDescription = stringResource(artwork.description),
            contentScale = ContentScale.Fit,
            modifier = Modifier.padding(20.dp).fillMaxWidth().height(imageHeight)
        )
    }
}

@Composable
private fun ArtworkDescriptor(artwork: Artwork, index: Int, modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.surfaceVariant) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(stringResource(artwork.title), style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Light)
            Text(stringResource(R.string.artist_and_year, stringResource(artwork.artist),
                stringResource(artwork.year)), style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium)
            Text(stringResource(R.string.artwork_position, index + 1, artworks.size),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun DisplayController(onPrevious: () -> Unit, onNext: () -> Unit, modifier: Modifier = Modifier) {
    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(24.dp)) {
        Button(onClick = onPrevious, modifier = Modifier.weight(1f)) {
            Text(stringResource(R.string.previous))
        }
        Button(onClick = onNext, modifier = Modifier.weight(1f)) {
            Text(stringResource(R.string.next))
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Preview(showBackground = true, widthDp = 1000, heightDp = 700)
@Composable
fun ArtSpacePreview() {
    ArtSpaceTheme { ArtSpaceScreen() }
}
