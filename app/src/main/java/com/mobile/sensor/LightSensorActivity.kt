package com.mobile.sensor

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class LightSensorActivity : AppCompatActivity(), SensorEventListener {
    lateinit var sensorManager: SensorManager
    var brightness: Sensor? = null
    lateinit var textView: TextView
    lateinit var progressBar: CircularProgressBar
    lateinit var layout: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light_sensor)

        textView = findViewById(R.id.textView)
        progressBar = findViewById(R.id.progress_circular)
        layout = findViewById(R.id.layout)
        setUpSensor()
    }

    private fun setUpSensor() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        brightness = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    private fun brightness(brightness: Float): String {
        return when (brightness.toInt()) {
            0 -> "Pitch Black"
            in 1..10 -> "Dark"
            in 11..50 -> "Grey"
            in 51..5000 -> "Normal"
            in 5001..25000 -> "Incredibly Bright"
            else -> "This light will blind you"
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_LIGHT) {
            val light = event.values[0]

            textView.text = "Sensor ${light}\n${brightness(light)}"
            progressBar.setProgressWithAnimation(light)
//            layout.setBackgroundColor(Color.parseColor(Utils.convert(brightness(light))))
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            this, brightness,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}