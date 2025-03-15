package com.example.tugasandroidtodolist

import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var layout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layout = findViewById(R.id.taskContainer)
        val addButton: ImageButton = findViewById(R.id.addButton)
        val greetingText: TextView = findViewById(R.id.greetingText)

        greetingText.text = "Hallo, User!"

        addButton.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Tambah To-Do")

        val input = EditText(this).apply {
            inputType = InputType.TYPE_CLASS_TEXT // Mencegah multi-line (Enter)
            filters = arrayOf(InputFilter.LengthFilter(100)) // Batas karakter maksimal (opsional)
        }

        builder.setView(input)

        builder.setPositiveButton("Yes") { _, _ ->
            val taskText = input.text.toString().trim()
            val wordCount = taskText.split("\\s+".toRegex()).size

            if (taskText.isNotEmpty() && wordCount <= 10) {
                addTask(taskText)
            } else {
                Toast.makeText(this, "Maksimal 10 kata!", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun addTask(task: String) {
        val radioButton = RadioButton(this)
        radioButton.text = task
        radioButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                radioButton.paintFlags = radioButton.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                radioButton.paintFlags = radioButton.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
        layout.addView(radioButton)
    }
}
