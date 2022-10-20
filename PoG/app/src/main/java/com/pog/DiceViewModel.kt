package com.pog

import android.R.attr.gravity
import android.app.Activity
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


class DiceViewModel : Activity(), SensorEventListener {

    private var sensorManager : SensorManager ?= null
    private var tiltSensor : Sensor ?= null
    private var choice : RadioGroup ?= null
    private var result : TextView ?= null
    private var buttonOne : Button ?= null
    private var buttonTwo : Button ?= null
    private var buttonThree : Button ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dice_view)


        choice = findViewById<RadioGroup>(R.id.radio_group)
        result = findViewById<TextView>(R.id.textView)
        buttonOne = findViewById<RadioButton>(R.id.one)
        buttonTwo = findViewById<RadioButton>(R.id.two)
        buttonThree = findViewById<RadioButton>(R.id.three)
        choice!!.check(buttonOne!!.id)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        tiltSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            }

    private fun getRandomNumber(): Int {
        return Random.nextInt(1, 7)
    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this,tiltSensor,
            SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Do nothing
    }

    override fun onSensorChanged(event: SensorEvent) {
        val alpha = 0.8.toFloat()
        val gravityValues = FloatArray(3)
        gravityValues[0] = alpha * gravityValues[0] + (1 - alpha) * event.values[0]
        gravityValues[1] = alpha * gravityValues[1] + (1 - alpha) * event.values[1]
        gravityValues[2] = alpha * gravityValues[2] + (1 - alpha) * event.values[2]
        val linearAcceleration = FloatArray(3)
        linearAcceleration[0] = event.values[0] - gravityValues[0]
        linearAcceleration[1] = event.values[1] - gravityValues[1]
        linearAcceleration[2] = event.values[2] - gravityValues[2]
        if(linearAcceleration[0] > 1 || linearAcceleration[1] > 10 || linearAcceleration[2] > 1) {
            val result = findViewById<TextView>(R.id.textView)
            if(choice!!.checkedRadioButtonId == buttonOne!!.id) {
                result.text = getRandomNumber().toString()
            } else if(choice!!.checkedRadioButtonId == buttonTwo!!.id) {
                result.text = "${getRandomNumber()} \n${getRandomNumber()}"
            } else {
                result.text = "${getRandomNumber()} \n${getRandomNumber()} \n${getRandomNumber()}"
            }
        }
    }
}
