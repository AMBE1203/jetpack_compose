package com.example.layoutbasic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.EmphasisAmbient
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideEmphasis
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.layoutbasic.ui.LayoutBasicTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutBasicTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    PhotographerCard()
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LayoutBasicTheme {
        PhotographerCard()
    }
}

@Composable
fun PhotographerCard(modifier: Modifier = Modifier) {
    Row(
        modifier.padding(8.dp).clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.surface).clickable(
                onClick = {}
            ).padding(16.dp)
    ) {
        Surface(
            modifier = Modifier.preferredSize(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {
            // image goes here
        }

        Column(modifier = Modifier.padding(start = 8.dp).gravity(Alignment.CenterVertically)) {
            Text(text = "Alfred Sisley", fontWeight = FontWeight.Bold)
            ProvideEmphasis(emphasis = EmphasisAmbient.current.medium) {
                Text(text = "3 minutes ago", style = MaterialTheme.typography.body2)

            }
        }
    }
}