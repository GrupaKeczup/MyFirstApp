package com.example.myfirstproject

import android.R.attr
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.lang.Math.abs
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var buttonsCounter = 0

        val buttonPlus = findViewById<Button>(R.id.buttonPlus)
        val buttonMinus = findViewById<Button>(R.id.buttonMinus)
        val slider = findViewById<SeekBar>(R.id.slider)

        val buttonsCounterTextView = findViewById<TextView>(R.id.buttonsCounter)
        val sliderValueTextView = findViewById<TextView>(R.id.sliderValue)

        val buttonImages = arrayOf(
            R.drawable.alcedo,
            R.drawable.dog,
            R.drawable.hedgehog,
            R.drawable.mammals,
            R.drawable.rabbit
        )

        setButtonImage(buttonsCounter, buttonImages, buttonPlus)
        setButtonImage(buttonsCounter, buttonImages, buttonMinus)

        slider.progress = loadValue()
        sliderValueTextView.text = addZeros(slider.progress)

        buttonPlus.setOnClickListener {
            buttonsCounter++
            buttonsCounterTextView.text = addZeros(buttonsCounter)
            setButtonImage(buttonsCounter, buttonImages, buttonPlus)
        }

        buttonMinus.setOnClickListener {
            lifecycleScope.launch {
                val bitmap = httpGet("https://picsum.photos/id/" + kotlin.math.abs(buttonsCounter) +"/400/200.jpg")
                buttonMinus.background = BitmapDrawable(resources, bitmap)

                buttonsCounter--
                buttonsCounterTextView.text = addZeros(buttonsCounter)
            }
        }

        slider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, value: Int, b: Boolean) {
                sliderValueTextView.text = addZeros(value)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
    }

    override fun onStop() {
        val slider = findViewById<SeekBar>(R.id.slider)
        saveValue(slider.progress)
        super.onStop()
    }

    fun addZeros (number : Int) : String {
        return if (number >= 0) {
            number.toString().padStart(3, '0')
        } else {
            val positiveNumber = number * -1
            "-" + positiveNumber.toString().padStart(3, '0')
        }
    }

    private fun setButtonImage (imageIndex : Int, imagesArray : Array<Int>, button : Button) {
        var newIndex = imageIndex % imagesArray.size

        if (newIndex < 0) {
            newIndex *= -1
        }

        button.setBackgroundResource(imagesArray[newIndex])
    }

    private suspend fun httpGet(myURL: String): Bitmap {
        return withContext(Dispatchers.IO) {
            val inputStream : InputStream
            val url = URL(myURL)
            val conn : HttpURLConnection = url.openConnection() as HttpURLConnection

            conn.connect()
            inputStream = conn.inputStream
            BitmapFactory.decodeStream(inputStream)
        }
    }

    private fun saveValue(number : Int) {
        val sharedPreference = getSharedPreferences("MY_APP", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()

        editor.putInt("myNum", number)
        editor.apply()
    }

    private fun loadValue() : Int {
        val sharedPreference = getSharedPreferences("MY_APP", Context.MODE_PRIVATE)
        return sharedPreference.getInt("myNum", 0)
		// XD
    }
}
