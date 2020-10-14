package com.tes.mitra.mitratest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val row1 = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0")
    val row2 = arrayOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p")
    val row3 = arrayOf("a", "s", "d", "f", "g", "h", "j", "k", "l", ";")
    val row4 = arrayOf("z", "x", "c", "v", "b", "n", "m", ",", ".", "/")

    var keyboard : MutableList<String> = mutableListOf()
    val countPerRow = row1.size
    val totalRow = 4
    lateinit var tvKeyboard : TextView
    lateinit var btnSubmit : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initData()
        initAction()
    }

    private fun initView() {
        tvKeyboard = findViewById<TextView>(R.id.tv_keyboard)
        btnSubmit = findViewById<Button>(R.id.btn_submit)
    }

    private fun initData() {
        keyboard.addAll(row1)
        keyboard.addAll(row2)
        keyboard.addAll(row3)
        keyboard.addAll(row4)

        for(i in 0 until row1.size) {
            tvKeyboard.append(row1[i] + " ")
        }
        tvKeyboard.append("\n")
        for(i in 0 until row2.size) {
            tvKeyboard.append(row2[i] + " ")
        }
        tvKeyboard.append("\n")
        for(i in 0 until row3.size) {
            tvKeyboard.append(row3[i] + " ")
        }
        tvKeyboard.append("\n")
        for(i in 0 until row4.size) {
            tvKeyboard.append(row4[i] + " ")
        }
    }

    private fun initAction() {
        btnSubmit.setOnClickListener {
            val flipMethods = et_flip.text.toString().split(",")

            for(i in 0 until flipMethods.size) {
                val flip = flipMethods[i]

                if(flip == "") {
                    continue
                } else if(flip.toLowerCase() == "h") {
                    horizontal()
                } else if(flip.toLowerCase() == "v") {
                    vertical()
                } else {
                    try {
                        val flipInt = flip.toInt()
                        shiftBy(flipInt)
                    } catch (e : Exception) {
                        continue
                    }
                }
            }
        }
    }

    fun horizontal() {
        tvKeyboard.text = ""

        var newKey : MutableList<String> = mutableListOf()
        newKey.addAll(keyboard)

        for(i in 0 until totalRow) {
            var index = (countPerRow - 1) + (i * countPerRow)
            var indexSwap = i * countPerRow

            while (index >= (i * countPerRow)) {
                newKey.set(indexSwap, keyboard[index])
                tvKeyboard.append(keyboard[index] + " ")
                index -= 1
                indexSwap += 1

            }

            tvKeyboard.append("\n")
        }

        this.keyboard = newKey
        Log.d("keyboar", Gson().toJson(keyboard))
    }

    fun vertical() {
        tvKeyboard.text = ""

        var newKey : MutableList<String> = mutableListOf()
        newKey.addAll(keyboard)

        var rows = 3
        var indexrow = 0

        while(rows >= 0) {
            var index = rows * countPerRow
            var indexSwap = indexrow * countPerRow

            for(i in 0 until countPerRow) {
                newKey.set(indexSwap, keyboard[index])
                tvKeyboard.append(keyboard[index] + " ")
                index += 1
                indexSwap += 1
            }

            tvKeyboard.append("\n")
            rows -=1
            indexrow += 1
        }

        this.keyboard = newKey
        Log.d("keyboard", Gson().toJson(this.keyboard))

    }

    fun shiftBy(shift : Int) {

        tvKeyboard.text = ""

        var newKey : MutableList<String> = mutableListOf()
        newKey.addAll(keyboard)

        val allSize = keyboard.size
        val newChars = arrayOfNulls<String>(allSize)

        for(i in 0 until keyboard.size) {
            val temp = keyboard[i]
            val newIndex = (i + (allSize - shift)) % allSize
            newChars[newIndex] = temp
        }

        val rows = 4

        for(i in 0 until rows) {
            var indexrow = i * countPerRow

            for(j in 0 until countPerRow) {
                tvKeyboard.append(newChars[indexrow] + " ")
                newKey.set(indexrow, newChars[j + (i * countPerRow)]!!)
                indexrow += 1
            }
            tvKeyboard.append("\n")
        }
        this.keyboard = newKey
        Log.d("keyboard", Gson().toJson(this.keyboard))
    }

    fun onMessage(message : String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
}