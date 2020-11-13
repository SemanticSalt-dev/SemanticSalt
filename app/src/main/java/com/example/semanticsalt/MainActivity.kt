package com.example.semanticsalt

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hi.text = randomQuote()
        cost.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                //do what you want on the press of 'done'
                runCalculation.performClick()
            }
            false
        }
    }
    fun doSomethingMathish(v: View) {
        closeKeyboard()
        val id = v.id
        if (id == R.id.reset) {
            qty.setText("")
            cost.setText("")
            costLine.text = ""
            costLine2.text = ""
        } else if (id == R.id.runCalculation) {
            val totalQtyString = qty.text.toString()
            val totalCostString = cost.text.toString()
            if (totalQtyString.isBlank() && totalQtyString.isBlank()) {
                Toast.makeText(this, "You did not enter anything at all..", Toast.LENGTH_SHORT).show()
                return
            } else if (totalQtyString.isBlank()) {
                Toast.makeText(this, "You did not enter a quantity", Toast.LENGTH_SHORT).show()
                return
            } else if (totalCostString.isBlank()) {
                Toast.makeText(this, "You did not enter a cost", Toast.LENGTH_SHORT).show()
                return
            }
            hi.text = randomQuote()
            val totalQty = totalQtyString.toDouble()
            val totalCost = totalCostString.toDouble()
            val itemCost = totalCost / totalQty
            val roundedCost = roundThreeDecimals(itemCost)
            val oneLess = roundNoDecimals(totalQty) - 1
            val oneLessInt = roundNoDecimals(oneLess).toInt()
            val allButOne = roundedCost * oneLess
            val finalItem = roundThreeDecimals(totalCost - allButOne)
            val itemCostString = roundedCost.toString() + " X " + roundNoDecimals(oneLessInt.toDouble()).toInt()
            val finalItemString = roundThreeDecimals(finalItem).toString() + " X 1"
            when {
                finalItem == roundedCost -> {
                    val allCostString = roundTwoDecimals(roundedCost) + " X " + roundNoDecimals(totalQty).toInt()
                    costLine.text = allCostString
                    costLine2.text = getString(R.string.tooEasy)
                }
                roundThreeDecimals(roundedCost * oneLess + roundThreeDecimals(finalItem)) == roundThreeDecimals(totalCost) -> {
                    Log.d("totalQtyString", totalQtyString)
                    Log.d("totalCostString", totalCostString)
                    Log.d("totalQtyDouble", totalQty.toString())
                    Log.d("costTotalDouble", totalCost.toString())
                    Log.d("costItemDouble", itemCost.toString())
                    costLine.text = itemCostString
                    costLine2.text = finalItemString
                }
                else -> {
                    val errorString = roundTwoDecimals(roundedCost * oneLess + finalItem) + " in not = to " + roundTwoDecimals(totalCost)
                    costLine.text = getString(R.string.errorWarning)
                    costLine2.text = errorString
                }
            }
        }
    }

    private fun roundNoDecimals(d: Double): Double {
        val oneDForm = DecimalFormat("#")
        return oneDForm.format(d).toDouble()
    }

    private fun roundTwoDecimals(d: Double): String {
        val twoDForm = DecimalFormat("$#,##0.00")
        return twoDForm.format(d)
    }

    private fun roundThreeDecimals(d: Double): Double {
        val threeDForm = DecimalFormat("#.###")
        return threeDForm.format(d).toDouble()
    }

    private fun closeKeyboard() {
        try {
            val editTextInput = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            editTextInput.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            Log.e("AndroidView", "closeKeyboard: $e")
        }
    }

    private fun randomQuote(): String? {
        val res = resources
        val greetingArray = res.getStringArray(R.array.greetings)
        return greetingArray[Random().nextInt(greetingArray.size)]
    }

}