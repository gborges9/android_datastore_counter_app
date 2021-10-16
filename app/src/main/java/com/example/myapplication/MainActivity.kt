package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis


class MainActivity : AppCompatActivity() {
    lateinit var textInput: AppCompatEditText
    lateinit var increaseBtn: Button
    lateinit var decreaseBtn: Button
    lateinit var setValueBtn: Button

    lateinit var counterManager: CounterDataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textInput = findViewById(R.id.textInput)
        increaseBtn = findViewById(R.id.increaseBtn)
        decreaseBtn = findViewById(R.id.decreaseBtn)
        setValueBtn = findViewById(R.id.setBtn)

        counterManager = CounterDataStoreManager(this)

        // Collect the counter value and set the text everytime the value changes
        lifecycleScope.launch {
            counterManager.counter.collect { counter ->
                textInput.setText(counter.toString())
            }
        }

        // Increment the counter
        increaseBtn.setOnClickListener {
           lifecycleScope.launch {
               counterManager.incrementCounter()
           }
        }

        // Decrement the counter
        decreaseBtn.setOnClickListener {
            lifecycleScope.launch {
                counterManager.decrementCounter()
            }
        }

        // Set the current value of the counter
        setValueBtn.setOnClickListener {
            lifecycleScope.launch {
                counterManager.setCounter(textInput.text.toString().toInt())
            }
        }
    }
}