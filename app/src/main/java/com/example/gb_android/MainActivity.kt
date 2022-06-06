package com.example.gb_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.gb_android.databinding.CalculatorActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : CalculatorActivityBinding

    lateinit var state : State

    var lastNumeric : Boolean = false
    var lastDot : Boolean = false

    private val KEY_STATE = "STATE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CalculatorActivityBinding.inflate(layoutInflater)
        val style = intent.getIntExtra("STYLE", 0)

        theme.applyStyle(style, true)

        setContentView(binding.root)


        state  = savedInstanceState?.getParcelable(KEY_STATE) ?: State(
            number = "0",
            sign = "",
        )

        renderState()

    }


    private fun renderState() = with(binding){
        tvInput.setText(state.number.toString())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_STATE, state)

    }

    fun onEqual(view : View){
        if(lastNumeric){

            var expression = state.number

            if(expression!!.contains("-")){
                val splitValue = expression.split("-")
                var firstVal = splitValue[0]
                var secondVal = splitValue[1]

                state.number = removeZeroAfterDot((firstVal.toDouble() - secondVal.toDouble()).toString())
            }
            else if (expression.contains("*")){
                val splitValue = expression.split("*")
                var firstVal = splitValue[0]
                var secondVal = splitValue[1]

                state.number = removeZeroAfterDot((firstVal.toDouble() * secondVal.toDouble()).toString())
            }
            else if (expression.contains("/")){
                val splitValue = expression.split("/")
                var firstVal = splitValue[0]
                var secondVal = splitValue[1]


                state.number = removeZeroAfterDot((firstVal.toDouble() / secondVal.toDouble()).toString())
            }
            else if (expression.contains("+")){
                val splitValue = expression.split("+")
                var firstVal = splitValue[0]
                var secondVal = splitValue[1]

                state.number = removeZeroAfterDot((firstVal.toDouble() + secondVal.toDouble()).toString())
            }
            renderState()
        }
    }

    private fun removeZeroAfterDot(result : String) : String{
        var value = result
        if (value.contains(".0")){
            value = value.substring(0, result.length - 2)
        }
        return value
    }

    fun onClear(view : View){
        state.number = "0"
        lastNumeric = false
        lastDot = false
        renderState()
    }

    fun onOperator(view : View){
        var button = view as Button
        if(lastNumeric && !isOperatorAdded(binding.tvInput.text.toString())){
            state.number += button.text
            lastNumeric = false
            lastDot = false
        }
        renderState()
    }

    private fun isOperatorAdded(value : String) : Boolean {
        return if(value.startsWith("-")){
            false
        }
        else{
            value.contains("/") || value.contains("*") ||
                    value.contains("+") || value.contains("-")
        }
    }

    fun onDecimalPoint(view : View){
        if(lastNumeric && !lastDot){
            state.number += "."
            lastDot = true
        }
        renderState()
    }

    fun onDigit(view : View){
        val button = view as Button
        if(state.number == "0"){
            state.number = button.text as String
        }
        else{
            state.number = state.number + button.text
        }
        lastNumeric = true
        renderState()
    }

    class State(
        var number: String?,
        var sign : String?,
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(number)
            parcel.writeString(sign)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<State> {
            override fun createFromParcel(parcel: Parcel): State {
                return State(parcel)
            }

            override fun newArray(size: Int): Array<State?> {
                return arrayOfNulls(size)
            }
        }
    }

}