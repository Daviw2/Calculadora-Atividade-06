package com.example.calculadoraatividade06

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

private val AppBackground = Color(0xFF0B1220)
private val DisplayBackground = Color(0xFF111B2E)
private val NumberButton = Color(0xFF18243A)
private val FunctionButton = Color(0xFF263650)
private val OperatorButton = Color(0xFF5479F7)
private val EqualsButton = Color(0xFF7A5CF4)
private val SecondaryText = Color(0xFF91A4C5)

@Composable
fun CalculatorScreen(
    calculatorViewModel: CalculatorViewModel = viewModel()
) {
    val state = calculatorViewModel.uiState

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 18.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(
                        color = DisplayBackground,
                        shape = RoundedCornerShape(30.dp)
                    )
                    .padding(horizontal = 24.dp, vertical = 26.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.BottomEnd),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = state.expression.ifBlank { " " },
                        color = SecondaryText,
                        fontSize = 19.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = state.display,
                        color = Color.White,
                        fontSize = when {
                            state.display.length > 16 -> 34.sp
                            state.display.length > 11 -> 42.sp
                            else -> 54.sp
                        },
                        fontWeight = FontWeight.Light,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            CalculatorRow(
                items = listOf(
                    CalculatorKey("AC", FunctionButton) { calculatorViewModel.onClear() },
                    CalculatorKey("+/−", FunctionButton) { calculatorViewModel.onToggleSign() },
                    CalculatorKey("%", FunctionButton) { calculatorViewModel.onPercent() },
                    CalculatorKey("÷", OperatorButton) { calculatorViewModel.onOperator("/") }
                )
            )

            CalculatorRow(
                items = listOf(
                    CalculatorKey("7", NumberButton) { calculatorViewModel.onDigit("7") },
                    CalculatorKey("8", NumberButton) { calculatorViewModel.onDigit("8") },
                    CalculatorKey("9", NumberButton) { calculatorViewModel.onDigit("9") },
                    CalculatorKey("×", OperatorButton) { calculatorViewModel.onOperator("*") }
                )
            )

            CalculatorRow(
                items = listOf(
                    CalculatorKey("4", NumberButton) { calculatorViewModel.onDigit("4") },
                    CalculatorKey("5", NumberButton) { calculatorViewModel.onDigit("5") },
                    CalculatorKey("6", NumberButton) { calculatorViewModel.onDigit("6") },
                    CalculatorKey("−", OperatorButton) { calculatorViewModel.onOperator("-") }
                )
            )

            CalculatorRow(
                items = listOf(
                    CalculatorKey("1", NumberButton) { calculatorViewModel.onDigit("1") },
                    CalculatorKey("2", NumberButton) { calculatorViewModel.onDigit("2") },
                    CalculatorKey("3", NumberButton) { calculatorViewModel.onDigit("3") },
                    CalculatorKey("+", OperatorButton) { calculatorViewModel.onOperator("+") }
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CalculatorButton(
                    text = "0",
                    color = NumberButton,
                    onClick = { calculatorViewModel.onDigit("0") },
                    modifier = Modifier.weight(2f)
                )

                CalculatorButton(
                    text = ".",
                    color = NumberButton,
                    onClick = { calculatorViewModel.onDecimal() },
                    modifier = Modifier.weight(1f)
                )

                CalculatorButton(
                    text = "=",
                    color = EqualsButton,
                    onClick = { calculatorViewModel.onEquals() },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

private data class CalculatorKey(
    val text: String,
    val color: Color,
    val onClick: () -> Unit
)

@Composable
private fun CalculatorRow(items: List<CalculatorKey>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items.forEach { key ->
            CalculatorButton(
                text = key.text,
                color = key.color,
                onClick = key.onClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun CalculatorButton(
    text: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(68.dp),
        shape = RoundedCornerShape(22.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = Color.White
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp,
            pressedElevation = 0.dp
        ),
        contentPadding = ButtonDefaults.ContentPadding
    ) {
        Text(
            text = text,
            fontSize = 22.sp,
            fontWeight = if (text in listOf("+", "−", "×", "÷", "=")) {
                FontWeight.SemiBold
            } else {
                FontWeight.Normal
            }
        )
    }
}
