package com.pog

import android.R.attr.gravity
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


class DiceViewModel : AppCompatActivity(), SensorEventListener {

    var sensorManager : SensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
    var tiltSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dice_view)

        val choice = findViewById<RadioGroup>(R.id.radio_group)
        val result = findViewById<TextView>(R.id.textView)
        val buttonOne = findViewById<RadioButton>(R.id.one)
        val buttonTwo = findViewById<RadioButton>(R.id.two)
        val buttonThree = findViewById<RadioButton>(R.id.three)
        choice.check(buttonOne.id)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        tiltSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

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

    override fun onResume() {
        super.onResume()
//        tiltSensor?.also { tilt ->
//            sensorManager.registerListener(this, tilt, SensorManager.SENSOR_DELAY_NORMAL)
//        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Do something here if sensor accuracy changes.
    }

    override fun onSensorChanged(event: SensorEvent) {
        // alpha is calculated as t / (t + dT)
        // with t, the low-pass filter's time-constant
        // and dT, the event delivery rate
        val alpha = 0.8.toFloat()
        val gravityValues = FloatArray(3)
        gravityValues[0] = alpha * gravityValues[0] + (1 - alpha) * event.values[0]
        gravityValues[1] = alpha * gravityValues[1] + (1 - alpha) * event.values[1]
        gravityValues[2] = alpha * gravityValues[2] + (1 - alpha) * event.values[2]
        val linear_acceleration = FloatArray(3)
        linear_acceleration[0] = event.values[0] - gravityValues[0]
        linear_acceleration[1] = event.values[1] - gravityValues[1]
        linear_acceleration[2] = event.values[2] - gravityValues[2]
        if(linear_acceleration[0] > 1) {
            val result = findViewById<TextView>(R.id.textView)
            result.text ="pog"
        }
    }
}
