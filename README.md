# Calculadora - Atividade 06

Aplicativo Android de calculadora desenvolvido em **Kotlin com Jetpack Compose**.

## Funcionalidades

- Soma (+)
- Subtração (-)
- Multiplicação (×)
- Divisão (÷)
- Porcentagem (%)
- Alteração de sinal (+/-)
- Ponto decimal
- Limpar (AC)
- Resolver (=)
- Exibição da operação realizada na área superior
- Resultado em destaque
- Tratamento de divisão por zero

## Tecnologias

- Kotlin
- Jetpack Compose
- Material 3
- ViewModel
- Android Studio

## Estrutura principal

- `MainActivity.kt`: inicia a aplicação
- `CalculatorScreen.kt`: interface da calculadora
- `CalculatorViewModel.kt`: estado e lógica dos cálculos
- `ui/theme/Theme.kt`: tema visual

## Como executar

1. Extraia a pasta.
2. Abra o Android Studio.
3. Selecione **Open**.
4. Escolha a pasta `Calculadora_Atividade06`.
5. Aguarde o Gradle Sync terminar.
6. Se o Android Studio pedir o SDK 35, aceite a instalação.
7. Inicie um emulador Android ou conecte um celular com depuração USB.
8. Clique em **Run**.

Na primeira sincronização, o Gradle e as dependências podem ser baixados da internet.

## Sugestão de teste para o print da entrega

Faça, por exemplo:

`4900 + 15910 =`

O visor deverá mostrar a operação e o resultado `20810`.

Também teste rapidamente as outras operações antes de enviar o projeto.
