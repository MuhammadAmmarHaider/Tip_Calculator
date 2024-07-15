package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat
import kotlin.math.round

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipCalculatorTheme {
               Surface {
                   TipCalculatorApp()
               }
            }
        }
    }
}
private fun CalculateTip(amount:Double,tipPercent:Double=15.0,roundUp:Boolean):String
{
    var tip=tipPercent/100*amount
    if(roundUp)
    {
        tip= kotlin.math.ceil(tip)
    }

    return NumberFormat.getCurrencyInstance().format(tip)
}
@Composable
fun EditNumberField(value:String, onValueChange:(String)->Unit, @StringRes id:Int,@DrawableRes leadingIcon:Int ,keyboardOptions:KeyboardOptions, modifier: Modifier=Modifier)
{
    var placeholder= stringResource(id = id)
    TextField(value = value,
        onValueChange =onValueChange,
        modifier=modifier ,
        label={Text(text=placeholder)},
        singleLine = true,
        leadingIcon= {Icon(painter= painterResource(id = leadingIcon),null)},
        keyboardOptions= KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
    )
}
@Composable
fun RoundTheTipRow(roundUp:Boolean,onRoundUpChange:(Boolean)->Unit,modifier: Modifier=Modifier)
{
    val text= stringResource(id = R.string.round_up)
    Row(modifier= modifier
        .fillMaxWidth()
        .size(48.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(text = text)
        Switch(checked =roundUp , onCheckedChange =onRoundUpChange,modifier= modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.End) )
    }
}

@Composable
fun TipCalculatorApp(modifier: Modifier = Modifier) {
    var amountInput by remember {mutableStateOf("")}
    var tipPercent by remember { mutableStateOf("") }
    var roundUp by remember { mutableStateOf(false) }
    var amount=amountInput.toDoubleOrNull()?:0.0
    var percent=tipPercent.toDoubleOrNull()?:15.0
    val tip= CalculateTip(amount,percent,roundUp)
    if(roundUp==true)
    {

    }
    val heading= stringResource(id = R.string.calculate_tip)
    val desc= stringResource(id = R.string.tip_amount,tip)
    Column(modifier= modifier
        .fillMaxSize()
        .statusBarsPadding()
        .padding(horizontal = 40.dp)
        .verticalScroll(rememberScrollState())
        .safeDrawingPadding(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text=heading,modifier= modifier
            .padding(bottom = 16.dp, top = 40.dp)
            .align(Alignment.Start))
        EditNumberField(value=amountInput,{amountInput=it}, R.string.bill_amount,modifier= Modifier
            .padding(bottom = 32.dp)
            .fillMaxWidth(), keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            ,leadingIcon=R.drawable.money
        )
        EditNumberField(value = tipPercent, onValueChange ={tipPercent=it},R.string.tip_percentage ,modifier= modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp), keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            leadingIcon = R.drawable.percent)
        RoundTheTipRow(roundUp = roundUp, onRoundUpChange ={roundUp=it},modifier=modifier.padding(bottom=32.dp) )
        Text(text=desc, fontWeight = FontWeight.Bold, fontSize = 24.sp,style = MaterialTheme.typography.displaySmall)
        Spacer(modifier=modifier.height(150.dp))
    }


}

@Preview(showBackground = true)
@Composable
fun TipCalculatorPreview() {
    TipCalculatorTheme {
        TipCalculatorApp()
    }
}