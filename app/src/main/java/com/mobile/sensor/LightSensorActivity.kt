package com.mobile.sensor

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
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

    private val TAG = "LightSensorActivity"

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_LIGHT) {
            mapFun()
            val light = event.values[0]


            Log.d(TAG, "onSensorChanged: " + brightness(light))

            textView.text = "Sensor ${light}\n${brightness(light)}"
            progressBar.setProgressWithAnimation(light)

            Log.d(TAG, "onSensorChanged: " + getData(brightness(light)))
            layout.setBackgroundColor(Color.parseColor(getData(brightness(light))))
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


    companion object {
        var map = HashMap<String, String>()

        fun mapFun() {
            map["Pitch Black"] = "#000000"
            map["Dark"] = "#474747"
            map["Grey"] = "#979797"
            map["Normal"] = "#EAEAEA"
            map["Incredibly Bright"] = "#FFFCFC"
            map["This light will blind you"] = "#FFFFFF"
        }

        fun getData(str: String): String {
            return map[str].toString()
        }
    }
}