package com.navdroid.bmicalculator.app.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.navdroid.bmicalculator.app.Screen
import com.navdroid.bmicalculator.app.navigateTo
import com.navdroid.bmicalculator.app.theme.AppTheme
import com.navdroid.bmicalculator.app.theme.accentColor
import com.navdroid.bmicalculator.R
import com.navdroid.bmicalculator.app.theme.textStyle
import com.navdroid.bmicalculator.app.ui.widgets.*
import com.navdroid.bmicalculator.app.util.BmiCalculator

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Toolbar(
                title = stringResource(R.string.app_name)
            )
        },
        content = {
            Content()
        }
    )
}

@Composable
private fun Content() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val maleState = mutableStateOf(true)
            val femaleState = mutableStateOf(false)
            RoundedToggleButton(
                state = maleState,
                text = "Male",
                onClick = {
                    maleState.value = true
                    femaleState.value = false
                },
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f)
            )
            RoundedToggleButton(
                state = femaleState,
                text = "Female",
                onClick = {
                    femaleState.value = true
                    maleState.value = false
                },
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            )
        }
        val heightState = remember { mutableStateOf(170) }
        val weightState: MutableState<Int> = remember { mutableStateOf(62) }
        val ageState: MutableState<Int> = remember { mutableStateOf(20) }
        WeightAndAgeSelector(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(top = 16.dp),
            heightState = heightState,
            weightState = weightState,
            ageState = ageState
        )
        RoundedButton(
            text = "Lets Begin",
            onClick = {
                val bmi = BmiCalculator(
                    heightState.value,
                    weightState.value
                )
                navigateTo(Screen.Result(bmi))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
    }
}

@Composable
private fun WeightAndAgeSelector(
    modifier: Modifier = Modifier,
    heightState: MutableState<Int>,
    weightState: MutableState<Int>,
    ageState: MutableState<Int>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        HeightSelector(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 8.dp)
                .fillMaxHeight(),
            heightState = heightState
        )
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            setWeightAgeNumberWidget(
                label = "Weight",
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .fillMaxHeight(),
                pickerState = weightState
            )
            setWeightAgeNumberWidget(
                label = "Age",
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .fillMaxHeight(),
                pickerState = ageState
            )
        }
    }
}

@Composable
private fun HeightSelector(
    modifier: Modifier = Modifier,
    heightState: MutableState<Int>
) {
    val height = buildAnnotatedString {
        withStyle(
            style = SpanStyle(fontSize = 32.sp)
        ) { append(heightState.value.toString()) }
        append(" cm")
    }
    RoundedCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxHeight(),
                //.gravity(Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Height",
                modifier = ColumnChildModifier,
                style = LabelStyle
            )
            Slider(
                value = heightState.value.toFloat(),
                onValueChange = { heightState.value = it.toInt() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                valueRange = (1f..272f),
                colors = SliderDefaults.colors(
                    activeTrackColor = accentColor
                )
            )
            Text(
                text = height,
                modifier = ColumnChildModifier,
                style = textStyle
            )
        }
    }
}

@Composable
private fun setWeightAgeNumberWidget(
    label: String,
    modifier: Modifier = Modifier,
    pickerState: MutableState<Int>,
    range: IntRange = 1..100
) {
    RoundedCard(modifier = modifier) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp,Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = LabelStyle,
                modifier = ColumnChildModifier
            )
            Text(
                text = pickerState.value.toString(),
                style = ValueStyle,
                modifier = ColumnChildModifier
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = ColumnChildModifier.fillMaxWidth()
            ) {
                RoundIconButton(imageVector = Icons.Default.Add, onClick = {
                    if (pickerState.value < range.last) {
                        pickerState.value = pickerState.value + 1
                    }
                })
                RoundIconButton(imageVector = Icons.Default.Remove, onClick = {
                    if (pickerState.value > range.first) {
                        pickerState.value = pickerState.value - 1
                    }
                })
            }
        }
    }
}

@Preview
@Composable
private fun ScreenPreview() {
    AppTheme {
        HomeScreen()
    }
}

private val LabelStyle = TextStyle(
    color = Color.Black.copy(alpha = 0.6f),
    fontSize = 18.sp
)

private val ValueStyle = TextStyle(
    color = Color.Black.copy(alpha = 0.9f),
    fontSize = 32.sp
)

private val ColumnChildModifier = Modifier.padding(8.dp)    //.gravity(Alignment.CenterHorizontally)
