package com.example.adivinanumeromayorjuego

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.adivinanumeromayorjuego.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var numLeft: Int = 0
    private var numRight: Int = 0
    private var points = 0
    private var lostPoints = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        randomNumberGenerator()
        binding.btnLeft.setOnClickListener {
            reviewAnswer(true)
        }
        binding.btnRight.setOnClickListener {
            reviewAnswer(false)
        }
    }

    private fun randomNumberGenerator() {
        val random = Random()
        numLeft = random.nextInt(10)
        numRight = random.nextInt(10)
        binding.btnLeft.text = "?"
        binding.btnRight.text = "?"
        if (numLeft == numRight) {
            randomNumberGenerator()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun reviewAnswer(izq: Boolean) {
        binding.btnLeft.text = numLeft.toString()
        binding.btnRight.text = numRight.toString()
        if (izq && numLeft > numRight || !izq && numLeft < numRight) {
            //Ganar puntos
            points++
            if (points >= 5) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Felicidades ganaste")
                builder.setMessage("Felicitaciones ganaste :) ")
                builder.setPositiveButton("ok") { _: DialogInterface, _: Int -> }
                builder.show()
                points = 0
                lostPoints = 0
            }
        } else {
            //Perder puntos
            lostPoints++
            if (lostPoints >= 5) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Lo siento Perdiste")
                builder.setMessage("Lo siento Perdiste :( ")
                builder.setPositiveButton("ok") { _: DialogInterface, _: Int -> }
                builder.show()
                lostPoints = 0
                points = 0
            }
        }
        binding.textPointsEarned.text = "Puntos Ganados: $points"
        binding.textLostPoints.text = "Puntos Perdidos: $lostPoints"
        binding.btnNext.setOnClickListener {
            randomNumberGenerator()
        }
    }
}