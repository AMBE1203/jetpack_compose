package com.example.jetcharts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetcharts.model.PieChartDataModel
import com.example.jetcharts.ui.pie.PieChart
import com.example.jetcharts.ui.pie.SimpleSliceDrawer
import com.example.jetcharts.ui.theme.JetChartsTheme
import com.example.jetcharts.ui.theme.Margins

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetChartsTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(bodyContent = { PieChartContent() })


            }
        }
    }
}

@Composable
private fun PieChartContent() {
    val pieChartData = PieChartDataModel()
    var sliceThickness by remember { mutableStateOf(25f) }

    Column(
        modifier = Modifier.padding(
            horizontal = Margins.horizontal,
            vertical = Margins.vertical
        )
    ) {

        PieChartRow(pieChartDataModel = pieChartData, sliceThickness = sliceThickness)
        SliceThicknessRow(sliceThickness = sliceThickness, onValueUpdated = { sliceThickness = it })
        AddOrRemoveSliceRow(pieChartDataModel = pieChartData)

    }

}

@Composable
private fun PieChartRow(pieChartDataModel: PieChartDataModel, sliceThickness: Float) {
    Row(modifier = Modifier.fillMaxWidth().height(150.dp).padding(vertical = Margins.vertical)) {
        PieChart(
            pieChartData = pieChartDataModel.pieChartData,
            sliceDrawer = SimpleSliceDrawer(
                sliceThickness = sliceThickness
            )
        )
    }
}

@Composable
private fun SliceThicknessRow(sliceThickness: Float, onValueUpdated: (Float) -> Unit) {

    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = Margins.verticalLarge),
        verticalGravity = Alignment.CenterVertically
    ) {
        Text(
            text = "Slice thickness",
            modifier = Modifier.gravity(Alignment.CenterVertically)
                .padding(end = Margins.horizontal)
        )
        Slider(
            value = sliceThickness,
            onValueChange = {
                onValueUpdated(it)
            },
            valueRange = 10f.rangeTo(100f)
        )
    }

}

@Composable
private fun AddOrRemoveSliceRow(pieChartDataModel: PieChartDataModel) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = Margins.vertical),
        verticalGravity = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { pieChartDataModel.removeSlice() },
            enabled = pieChartDataModel.slices.size > 3,
            shape = CircleShape
        ) {
            Text(text = "-")
        }
        Row(
            modifier = Modifier.padding(horizontal = Margins.horizontal),
            verticalGravity = Alignment.CenterVertically
        ) {
            Text(text = "Slices: ")
            Text(
                text = pieChartDataModel.slices.count().toString(),
                style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
            )
        }

        Button(
            onClick = { pieChartDataModel.addSlice() },
            enabled = pieChartDataModel.slices.size < 9,
            shape = CircleShape
        ) {
            Text(text = "+")
        }

    }
}