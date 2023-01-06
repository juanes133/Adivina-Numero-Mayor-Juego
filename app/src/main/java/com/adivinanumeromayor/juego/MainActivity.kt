package com.adivinanumeromayor.juego

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.adivinanumeromayor.juego.databinding.ActivityMainBinding
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
        binding.btnLeft.isEnabled = false
        binding.btnRight.text = numRight.toString()
        binding.btnRight.isEnabled = false
        if (izq && numLeft > numRight || !izq && numLeft < numRight) {
            //Ganar puntos
            points++
            if (points >= 5) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.felicidades_ganaste))
                builder.setMessage(getString(R.string.felicitaciones_ganaste))
                builder.setPositiveButton(getString(R.string.ok)) { _: DialogInterface, _: Int ->
                    restartPoints()
                }
                builder.show()
                binding.btnLeft.isEnabled = true
                binding.btnRight.isEnabled = true

            }
        } else {
            //Perder puntos
            lostPoints++
            if (lostPoints >= 5) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.lo_siento_perdiste))
                builder.setMessage(getString(R.string.perdiste))
                builder.setPositiveButton(getString(R.string.ok)) { _: DialogInterface, _: Int ->
                    restartPoints()
                }
                builder.show()
                binding.btnLeft.isEnabled = true
                binding.btnRight.isEnabled = true
            }
        }
        binding.textPointsEarned.text = "${getString(R.string.puntos_ganados)} $points"
        binding.textLostPoints.text = "${getString(R.string.puntos_perdidos)} $lostPoints"
        binding.btnNext.setOnClickListener {
            randomNumberGenerator()
            binding.btnLeft.isEnabled = true
            binding.btnRight.isEnabled = true
        }
    }

    @SuppressLint("SetTextI18n")
    private fun restartPoints() {
        binding.btnLeft.text = "?"
        binding.btnRight.text = "?"
        points = 0
        lostPoints = 0
        binding.textPointsEarned.text = "${getString(R.string.puntos_ganados)} $points"
        binding.textLostPoints.text = "${getString(R.string.puntos_perdidos)} $lostPoints"
    }
}
