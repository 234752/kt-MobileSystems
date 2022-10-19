package com.pog

import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.lang.Math.sqrt
import kotlin.math.round
import kotlin.random.Random

class DiceViewModel : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dice_view)

        val choice = findViewById<RadioGroup>(R.id.radio_group)
        val result = findViewById<TextView>(R.id.textView)
        val buttonOne = findViewById<RadioButton>(R.id.one)
        val buttonTwo = findViewById<RadioButton>(R.id.two)
        val buttonThree = findViewById<RadioButton>(R.id.three)
        choice.check(buttonOne.id)

        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val tiltSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)



        choice.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val button: RadioButton = findViewById(checkedId)
                result.text = getRandomNumber().toString()
            })

        var selectedOption = choice.checkedRadioButtonId
        result.text=selectedOption.toString()

    }

    private fun getRandomNumber(): Int {
        return Random.nextInt(1, 7);
    }
}