package com.example.myrecyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class DetailHero : AppCompatActivity() {

    private lateinit var data : Hero
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_hero)

        supportActionBar?.hide()

        data = intent.getParcelableExtra("key_hero")!!

        val imgPhoto: ImageView = findViewById(R.id.tv_detail_photo)
        val tvName: TextView = findViewById(R.id.tv_detail_name)
        val tvDescription: TextView = findViewById(R.id.tv_detail_description)
        val tvDate : TextView = findViewById(R.id.input_taggal_rilis)
        val tvActor : TextView = findViewById(R.id.nama_actor)
        button= findViewById(R.id.action_share)

        imgPhoto.setImageResource(data.photo)
        tvName.text = data.name
        tvDescription.text = data.description
        tvDate.text = data.date
        tvActor.text = data.actor

        ShareAction()

    }

    private fun ShareAction() {
        button.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Share this information!\n${data.shareData()}")
            startActivity(Intent.createChooser(shareIntent, "Share With"))
        }
    }


}