package com.example.semanticsalt

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var spEditor: SharedPreferences.Editor
    private val df = DecimalFormat("#,###")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //init share pref
        sharedPreferences = getSharedPreferences("USER_INFO_SP", Context.MODE_PRIVATE)
        //set data to views
        theCount.text = sharedPreferences.getInt("PUSH_UP_COUNT", 0).toString()
        customEntry.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                doSomeMath(v)
                return@OnKeyListener true
            }
            false
        })

    }
    private fun closeKeyboard(v: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }
    fun doSomeMath(v: View) {
        when (v.id) {
            R.id.reset -> {
                val i = 0
                spEditor = sharedPreferences.edit()
                spEditor.putInt("PUSH_UP_COUNT", i)
                spEditor.apply()
                theCount.text = sharedPreferences.getInt("PUSH_UP_COUNT", 0).toString()
            }
            R.id.add10 -> {
                var g: Int = sharedPreferences.getInt("PUSH_UP_COUNT", 0)
                spEditor = sharedPreferences.edit()
                g += 10
                spEditor.putInt("PUSH_UP_COUNT", g)
                spEditor.apply()
                theCount.text = df.format(sharedPreferences.getInt("PUSH_UP_COUNT", 0)).toString()
            }
            R.id.mainLayout -> {
                closeKeyboard(v)
                var g: Int = sharedPreferences.getInt("PUSH_UP_COUNT", 0)
                spEditor = sharedPreferences.edit()
                g += 1
                spEditor.putInt("PUSH_UP_COUNT", g)
                spEditor.apply()
                theCount.text = df.format(sharedPreferences.getInt("PUSH_UP_COUNT", 0)).toString()

            }
            R.id.customEntry -> {
                closeKeyboard(v)
                var g: Int = sharedPreferences.getInt("PUSH_UP_COUNT", 0)
                spEditor = sharedPreferences.edit()
                if (!customEntry.text.toString().isBlank()) {
                    g += customEntry.text.toString().toInt()
                    spEditor.putInt("PUSH_UP_COUNT", g)
                    spEditor.apply()
                    theCount.text = df.format(sharedPreferences.getInt("PUSH_UP_COUNT", 0)).toString()}
            }
            R.id.enterCustomButton -> {
                closeKeyboard(v)
                var g: Int = sharedPreferences.getInt("PUSH_UP_COUNT", 0)
                spEditor = sharedPreferences.edit()
                if (!customEntry.text.toString().isBlank()) {
                    g += customEntry.text.toString().toInt()
                    spEditor.putInt("PUSH_UP_COUNT", g)
                    spEditor.apply()
                    theCount.text = df.format(sharedPreferences.getInt("PUSH_UP_COUNT", 0)).toString()}
            }
        }
    }
}