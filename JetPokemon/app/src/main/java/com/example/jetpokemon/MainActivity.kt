package com.example.jetpokemon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.jetpokemon.ui.JetPokemonTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetPokemonTheme {
                // A surface container using the 'background' color from the theme
               MeasuringScale()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetPokemonTheme {
        MeasuringScale()
    }
}

@Composable
fun ScaleLine(index: Int) {
    val isDivisibleBy10 = index % 10 == 0
    val surfaceColor = MaterialTheme.colors.surface
    val onSurfaceColor = MaterialTheme.colors.onSurface

    Column(modifier = Modifier.background(color = surfaceColor)) {
        Canvas(
            modifier = Modifier.padding(5.dp).preferredHeight(100.dp).preferredWidth(3.dp),
        ) {
            drawLine(
                color = onSurfaceColor,
                start = Offset(0f, 0f),
                end = Offset(0f, if (isDivisibleBy10) size.height else size.height * 0.2f),
                strokeWidth = if (isDivisibleBy10) size.width else size.width * 0.3f
            )
        }

        Text(
            text = if (isDivisibleBy10) "${index / 10}" else "",
            textAlign = TextAlign.Center,
            style = TextStyle(fontFamily = FontFamily.Monospace),
            color = onSurfaceColor,
            modifier = Modifier.fillMaxWidth()
        )
    }

}


@Composable
fun ScaleCenterPointer() {
    val primaryColor = MaterialTheme.colors.primary

    Column {
        Canvas(modifier = Modifier.padding(5.dp).preferredHeight(120.dp).preferredWidth(3.dp)) {
            drawLine(
                color = primaryColor,
                start = Offset(0f, 0f),
                end = Offset(0f, size.height),
                strokeWidth = size.width
            )
        }
    }

}

@Composable
fun MeasuringScale() {
    ScrollableRow(modifier = Modifier.padding(top = 16.dp).fillMaxWidth(), children = {
        for (i in -20..1020) {
            ScaleLine(index = i)
        }
    })

    Box(gravity = ContentGravity.Center, modifier = Modifier.padding(top = 16.dp).fillMaxWidth()) {
        ScaleCenterPointer()
    }
}
