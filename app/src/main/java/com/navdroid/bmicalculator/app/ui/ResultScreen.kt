package com.navdroid.bmicalculator.app.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.navdroid.bmicalculator.app.ui.widgets.Toolbar

import com.navdroid.bmicalculator.app.Screen
import com.navdroid.bmicalculator.app.navigateTo
import com.navdroid.bmicalculator.app.util.BmiCalculator
import com.navdroid.bmicalculator.R
import com.navdroid.bmicalculator.app.theme.AppTheme
import com.navdroid.bmicalculator.app.theme.accentColor
import com.navdroid.bmicalculator.app.theme.foregroundColor
import com.navdroid.bmicalculator.app.theme.textStyle
import com.navdroid.bmicalculator.app.ui.widgets.RoundIconButton
import com.navdroid.bmicalculator.app.ui.widgets.RoundedButton

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ResultScreen(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    bmi: BmiCalculator
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Toolbar(
                title = stringResource(R.string.bmi_results),
                navigationIcon = {
                    RoundIconButton(
                        imageVector = Icons.Outlined.ArrowBack,
                        onClick = { navigateTo(Screen.Home) }
                    )
                }
            )
        },
        content = {
            Content(bmi)
        }
    )
}

@Composable
private fun Content(result: BmiCalculator) {
    Column(
        modifier = Modifier.padding(16.dp).fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        val childModifier = Modifier.align(Alignment.CenterHorizontally)
        Card(
            shape = CircleShape,
            elevation = 4.dp,
            backgroundColor = MaterialTheme.colors.background,
            modifier = childModifier
        ) {
            Card(
                shape = CircleShape,
                modifier = Modifier.padding(32.dp),
                backgroundColor = accentColor
            ) {
                Box(
                    modifier = Modifier
                        .background(color = MaterialTheme.colors.background,shape = CircleShape)
                        .size(112.dp)
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = result.bmiString,
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black.copy(alpha = 0.8f),
                            fontSize = 32.sp
                        )
                    )
                }
            }
        }
        Text(
            style = textStyle.copy(
                fontSize = 18.sp
            ),
            text = buildAnnotatedString {
                append("You have ")
                withStyle(
                    style = SpanStyle(
                        color = accentColor,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(result.result)
                }
                append(" body weight!")
            },
            modifier = childModifier
        )
    }
}

@Preview
@Composable
private fun ScreenPreview() {
    AppTheme {
        ResultScreen(bmi = BmiCalculator(202, 62))
    }
}
