package com.example.semanticsalt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        count.text = id.toString()

        plusBtn.setOnClickListener {
            ++id
            count.text = id.toString()
        }

        minusBtn.setOnClickListener {
            --id
            count.text = id.toString()
        }
    }

}