package com.example.gb_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.google.android.material.radiobutton.MaterialRadioButton


import android.widget.RadioGroup
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.util.Log
import android.widget.Button


class SettingsActivity : AppCompatActivity() {

    val MyCoolCodeStyle = 0
    val AppThemeLightCodeStyle = 1
    val AppThemeCodeStyle = 2
    val AppThemeDarkCodeStyle = 3
    var chosenTheme : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initThemeChooser()

        val button = findViewById<Button>(R.id.goToCalc)

        button.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            intent.putExtra("STYLE", chosenTheme)
            startActivity(intent)
        }

    }

    private fun codeStyleToStyleId(codeStyle: Int): Int {
        return when (codeStyle) {
            AppThemeLightCodeStyle -> R.style.AppThemeLight
            AppThemeDarkCodeStyle -> R.style.AppThemeDark
            else -> R.style.MyCoolStyle
        }
    }

    private fun initThemeChooser() {
        initRadioButton(
            findViewById(R.id.radioButtonMyCoolStyle),
            MyCoolCodeStyle
        )
        initRadioButton(
            findViewById(R.id.radioButtonMaterialDark),
            AppThemeDarkCodeStyle
        )
        initRadioButton(
            findViewById(R.id.radioButtonMaterialLight),
            AppThemeLightCodeStyle
        )
        initRadioButton(
            findViewById(R.id.radioButtonMaterialLightDarkAction),
            AppThemeCodeStyle
        )
    }

    private fun initRadioButton(button: View, codeStyle: Int) {
        button.setOnClickListener {
            chosenTheme = codeStyleToStyleId(codeStyle)
        }
    }

}