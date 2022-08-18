package com.cioffi.soslight

import android.content.Intent
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.cioffi.soslight.helpers.CharToMorse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking



class EncodeActivity : AppCompatActivity() {
    var flashLightStatus: Boolean = false
    var strToEncode = ""

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encode)

        val intentStr = intent.getStringExtra("stringToEncode")
        if (intentStr != null) {
            strToEncode = intentStr
        }
        // Capture the layout's TextView and set the string as its text
        val textView = findViewById<TextView>(R.id.encodeMors).apply {
            text = strToEncode
        }

        window.decorView.post {
            startMorsCode(this.strToEncode)
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun startMorsCode(word : String) = runBlocking { // this: CoroutineScope
        launch { openFlashLight(word)}
    }

    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun openFlashLight(phrase: String) {
        phrase.replace("\\s".toRegex(), "/")
        var phChars = phrase.toList()
        //char in phrase
        for (morsChar in phChars) {
            Toast.makeText(this@EncodeActivity, "Letter $morsChar", Toast.LENGTH_SHORT).show()
            val morsCharList = CharToMorse.convertCharToMors(morsChar)
            val cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager
            val cameraId = cameraManager.cameraIdList[0]

            for (mrChr in morsCharList) {
                when (mrChr) {
                    //dot case To symbolize dots, turn your light on for 1 second.
                    '.' -> {
                        torchOn(cameraManager, cameraId)
                        delay(1000)
                        torchOff(cameraManager, cameraId)

                    }
                    //dash case To symbolize dashes, turn your light on for 3 seconds.
                    '-' -> {
                        torchOn(cameraManager, cameraId)
                        delay(3000)
                        torchOff(cameraManager, cameraId)

                    }
                    // slash case The pause between complete words is 7 seconds with the light off.
                    '/' -> {
                        delay(6000)
                    }
                }
                delay(1000)
            }
            //wait 3 sec from on char to another
            delay(3000)
        }
    }

    //Torch on
    @RequiresApi(Build.VERSION_CODES.M)
    private fun torchOn(cameraManager: CameraManager, cameraId: String) {
        cameraManager.setTorchMode(cameraId, true)
        flashLightStatus = true
    }

    //Torch off
    @RequiresApi(Build.VERSION_CODES.M)
    private fun torchOff(cameraManager: CameraManager, cameraId: String) {
        cameraManager.setTorchMode(cameraId, false)
        flashLightStatus = false
    }



    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}