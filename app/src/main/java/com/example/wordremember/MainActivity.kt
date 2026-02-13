package com.example.wordremember

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.format.DateFormat
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.Date


class MainActivity : AppCompatActivity() {
    private lateinit var btnAdd:Button
    private lateinit var btnSave:Button
    private lateinit var tableLayout: TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.scrollArea)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tableLayout = findViewById(R.id.tableLayout)
        btnAdd = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener {
            Log.e("MSG","HEY HEY HEY")


            // Yeni TableRow oluştur
            val tableRow = TableRow(this)

            // Layout parametreleri
            tableRow.layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
            )

            // 1. EditText
            val word = EditText(this)
            word.hint = "Word"
            word.setPadding(12, 12, 12, 12)
            word.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)

            // 2. EditText
            val antonym = EditText(this)
            antonym.hint = "Antonym"
            antonym.setPadding(12, 12, 12, 12)
            antonym.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)

            // 3. EditText
            val past = EditText(this)
            past.hint = "Past"
            past.setPadding(12, 12, 12, 12)
            past.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)

            // Row içine ekle
            tableRow.addView(word)
            tableRow.addView(antonym)
            tableRow.addView(past)

            // TableLayout'a ekle
            tableLayout.addView(tableRow)
        }

        btnSave = findViewById(R.id.btnSave)
        btnSave.setOnClickListener {
            Log.e("MSG","LAW LAW LAW")

            takeScreenshot()

        }
    }


    //--------ScreenShot Codes-------------
    fun takeScreenshot() {
        val rootView = window.decorView.rootView
        val bitmap = Bitmap.createBitmap(
            rootView.width,
            rootView.height,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        rootView.draw(canvas)

        saveBitmap(bitmap)
    }
    fun saveBitmap(bitmap: Bitmap) {
        val filename = "screenshot_${System.currentTimeMillis()}.png"

        val fos: OutputStream?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver = contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/Screenshots")
            }

            val imageUri = resolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )

            fos = imageUri?.let { resolver.openOutputStream(it) }
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
    }

    fun addLine(){

    }




}
