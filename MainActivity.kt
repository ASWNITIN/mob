package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val name = findViewById<EditText>(R.id.edit_name)
        val age = findViewById<EditText>(R.id.edit_age)
        val insert = findViewById<Button>(R.id.insert)
        val view = findViewById<Button>(R.id.view)
        val delete = findViewById<Button>(R.id.delete)
        val update = findViewById<Button>(R.id.update)
        val result = findViewById<TextView>(R.id.textView4)

        val context = this
        val db = Database(context)

        insert.setOnClickListener {
            val user = Users(name.text.toString(), age.text.toString().toInt())
            db.insertData(user)
        }

        view.setOnClickListener {
            result.text = "" // Clear previous text
            val data = db.readData()
            for (i in data.indices) {
                result.append(data[i].name + " " + data[i].age + "\n")
            }
        }

        delete.setOnClickListener {
            val userName = name.text.toString()
            db.deleteData(userName)
            name.text.clear()
            age.text.clear()
            result.text = "User '$userName' deleted successfully."
        }

        update.setOnClickListener {
            val userName = name.text.toString()
            val userAge = age.text.toString().toInt()
            db.updateData(userName, userAge)
            result.text = "User '$userName' updated successfully."
        }
    }
}
