package com.pog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.lang.Math.sqrt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var result = findViewById<TextView>("TextViewResult")
    }

    fun calculateResult(a : Double, b : Double, c : Double): String {
        var d = b*b - 4*a*c
        if(d > 0) {
            var x1 = (sqrt(d) - b)/(2*a)
            var x2 = (sqrt(d) + b)/(2*a)
            return "Results: x\u2081 = $x1, x\u2082 = $x2"
        }
        else return "pog"
    }
}