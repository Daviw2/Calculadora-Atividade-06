package com.example.calculadoraatividade06

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

data class CalculatorUiState(
    val display: String = "0",
    val expression: String = ""
)

class CalculatorViewModel : ViewModel() {

    var uiState by mutableStateOf(CalculatorUiState())
        private set

    private var firstOperand: BigDecimal? = null
    private var pendingOperator: String? = null
    private var waitingForOperand = false
    private var justCalculated = false

    private val mathContext = MathContext(14, RoundingMode.HALF_UP)

    fun onDigit(digit: String) {
        if (uiState.display == "Erro") clear()

        val currentDigits = uiState.display.count { it.isDigit() }
        if (!waitingForOperand && !justCalculated && currentDigits >= 15) return

        if (waitingForOperand || justCalculated) {
            uiState = CalculatorUiState(
                display = digit,
                expression = if (waitingForOperand) uiState.expression else ""
            )
            waitingForOperand = false
            justCalculated = false
            return
        }

        val newDisplay = if (uiState.display == "0") {
            digit
        } else {
            uiState.display + digit
        }

        uiState = uiState.copy(display = newDisplay)
    }

    fun onDecimal() {
        if (uiState.display == "Erro") clear()

        if (waitingForOperand || justCalculated) {
            uiState = CalculatorUiState(
                display = "0.",
                expression = if (waitingForOperand) uiState.expression else ""
            )
            waitingForOperand = false
            justCalculated = false
            return
        }

        if (!uiState.display.contains(".")) {
            uiState = uiState.copy(display = uiState.display + ".")
        }
    }

    fun onClear() {
        clear()
    }

    fun onToggleSign() {
        if (uiState.display == "Erro") return

        val value = uiState.display.toBigDecimalOrNull() ?: return
        if (value.compareTo(BigDecimal.ZERO) == 0) return

        val changed = if (uiState.display.startsWith("-")) {
            uiState.display.drop(1)
        } else {
            "-" + uiState.display
        }

        uiState = uiState.copy(display = changed)
    }

    fun onPercent() {
        if (uiState.display == "Erro") return

        val value = uiState.display.toBigDecimalOrNull() ?: return
        val result = value.divide(BigDecimal("100"), mathContext)

        val percentageText = "${format(value)}%"
        val newExpression = if (pendingOperator != null && firstOperand != null) {
            "${format(firstOperand!!)} ${operatorSymbol(pendingOperator!!)} $percentageText"
        } else {
            percentageText
        }

        uiState = uiState.copy(
            display = format(result),
            expression = newExpression
        )

        waitingForOperand = false
        justCalculated = false
    }

    fun onOperator(operator: String) {
        if (uiState.display == "Erro") return

        // Se o usuário tocar em outro operador antes de informar o segundo número,
        // apenas substituímos o operador anterior.
        if (waitingForOperand && firstOperand != null) {
            pendingOperator = operator
            uiState = uiState.copy(
                expression = "${format(firstOperand!!)} ${operatorSymbol(operator)}"
            )
            return
        }

        val current = uiState.display.toBigDecimalOrNull() ?: return

        if (pendingOperator != null && firstOperand != null) {
            val result = calculate(firstOperand!!, current, pendingOperator!!)
            if (result == null) {
                showError()
                return
            }

            firstOperand = result
            uiState = uiState.copy(display = format(result))
        } else {
            firstOperand = current
        }

        pendingOperator = operator
        waitingForOperand = true
        justCalculated = false

        uiState = uiState.copy(
            expression = "${format(firstOperand!!)} ${operatorSymbol(operator)}"
        )
    }

    fun onEquals() {
        if (uiState.display == "Erro") return

        val first = firstOperand ?: return
        val operator = pendingOperator ?: return
        val second = uiState.display.toBigDecimalOrNull() ?: return

        val result = calculate(first, second, operator)
        if (result == null) {
            showError()
            return
        }

        uiState = CalculatorUiState(
            display = format(result),
            expression = "${format(first)} ${operatorSymbol(operator)} ${format(second)} ="
        )

        firstOperand = null
        pendingOperator = null
        waitingForOperand = false
        justCalculated = true
    }

    private fun calculate(
        first: BigDecimal,
        second: BigDecimal,
        operator: String
    ): BigDecimal? {
        return when (operator) {
            "+" -> first.add(second, mathContext)
            "-" -> first.subtract(second, mathContext)
            "*" -> first.multiply(second, mathContext)
            "/" -> {
                if (second.compareTo(BigDecimal.ZERO) == 0) null
                else first.divide(second, mathContext)
            }
            else -> second
        }
    }

    private fun format(value: BigDecimal): String {
        if (value.compareTo(BigDecimal.ZERO) == 0) return "0"
        return value.stripTrailingZeros().toPlainString()
    }

    private fun operatorSymbol(operator: String): String {
        return when (operator) {
            "*" -> "×"
            "/" -> "÷"
            "-" -> "−"
            else -> operator
        }
    }

    private fun showError() {
        uiState = CalculatorUiState(
            display = "Erro",
            expression = "Não é possível dividir por zero"
        )
        firstOperand = null
        pendingOperator = null
        waitingForOperand = false
        justCalculated = true
    }

    private fun clear() {
        uiState = CalculatorUiState()
        firstOperand = null
        pendingOperator = null
        waitingForOperand = false
        justCalculated = false
    }
}
