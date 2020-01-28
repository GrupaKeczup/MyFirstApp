package com.example.myfirstproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView

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

        buttonPlus.setOnClickListener {
            buttonsCounter++
            buttonsCounterTextView.text = addZeros(buttonsCounter)
            setButtonImage(buttonsCounter, buttonImages, buttonPlus)
        }

        buttonMinus.setOnClickListener {
            buttonsCounter--
            buttonsCounterTextView.text = addZeros(buttonsCounter)
            setButtonImage(buttonsCounter, buttonImages, buttonMinus)
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

    fun addZeros (number : Int) : String {
        return if (number >= 0) {
            number.toString().padStart(3, '0')
        } else {
            val positiveNumber = number * -1
            "-" + positiveNumber.toString().padStart(3, '0')
        }
    }

    fun setButtonImage (imageIndex : Int, imagesArray : Array<Int>, button : Button) {
        var newIndex = imageIndex % imagesArray.size

        if (newIndex < 0) {
            newIndex *= -1
        }

        button.setBackgroundResource(imagesArray[newIndex])
    }
}
