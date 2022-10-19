package com.pog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.lang.Math.sqrt
import kotlin.math.round

class EquationViewModel : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.equation_view)

        var result = findViewById<TextView>(R.id.textViewResult)
        result.text = ""
        var button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            try {
                val a : Double = findViewById<EditText>(R.id.editTextA).text.toString().toDouble()
                val b : Double = findViewById<EditText>(R.id.editTextB).text.toString().toDouble()
                val c : Double = findViewById<EditText>(R.id.editTextC).text.toString().toDouble()
                result.text = calculateResult(a, b, c)
            } catch (e : Exception) {
                result.text = "Invalid data, try again"
            }
        }
    }

    fun calculateResult(a : Double, b : Double, c : Double): String {

        if(a == 0.0) {
            var x = round(-c/b*100)/100
            return "It's a linear equation, result: \nx = $x"
        }

        var d = b*b - 4*a*c
        if(d > 0) {
            var x1 = round((sqrt(d) - b)/(2*a)*100)/100
            var x2 = round((sqrt(d) + b)/(2*a)*100)/100
            return "Results: " +
                    "\ndiscriminant = $d" +
                    "\nx\u2081 = $x1 \nx\u2082 = $x2"
        } else if(d == 0.0) {
            var x0 = round(-b/(2*a)*100)/100
            return "Result: " +
                    "\ndiscriminant = $d" +
                    "\nx\u2080 = $x0"
        } else {
            return "Discriminant = $d < 0, no results in this case"
        }
    }
}