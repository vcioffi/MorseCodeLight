package com.cioffi.soslight

import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.cioffi.soslight.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var flashLightStatus: Boolean = false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        val generateSOS: Button = findViewById(R.id.morsBtn)
        generateSOS.setOnClickListener{
            var morseCode = executeMessage("SOS")
            openFlashLight(morseCode)
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun openFlashLight( mcode : MutableList<String>) {
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList[0]
       //Loop over the words
        for(word in mcode) {
            var morsChars = word.toList()
            // Loop over the signle component of a char The time gap between full letters is 3 seconds with the light off.
            for (morsChar in morsChars) {
                val morsChar = morsChar.toString()
                when (morsChar) {
                    //dot case To symbolize dots, turn your light on for 1 second.
                    "." -> {
                        cameraManager.setTorchMode(cameraId, true)
                        flashLightStatus = true
                        Thread.sleep(1000)
                            cameraManager.setTorchMode(cameraId, false)
                            flashLightStatus = false

                    }
                    //dash case To symbolize dashes, turn your light on for 3 seconds.
                    "-" -> {
                        cameraManager.setTorchMode(cameraId, true)
                        flashLightStatus = true
                        Thread.sleep(3000)
                            cameraManager.setTorchMode(cameraId, false)
                            flashLightStatus = false
                    }
                    // slash case The pause between complete words is 7 seconds with the light off.
                    "/" -> {

                    }

                    else -> {
                    }
                }
                cameraManager.setTorchMode(cameraId, false)
                flashLightStatus = false
                Thread.sleep(1000)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun executeMessage(s: String): MutableList<String> {
        var chars = s.toList()
        val tranCode = DbConstants.mapChar
        var morsCode : MutableList<String> = arrayListOf()
        var finalMorcCode=""

        for(el in chars) {
            val element = el.toString()
            if(tranCode.containsKey(element)) {
                tranCode.get(element)?.let { morsCode.add(it) }
            }
            println(el) // or your logic to catch the "B"
            Log.i("Char is ","el")
        }
        finalMorcCode = morsCode.joinToString(",")
        Log.i("Final is ","$finalMorcCode")
        return morsCode
    }



    object DbConstants {
        val mapChar = mapOf(" " to "/","S" to "...", "O" to "---")
    }

}