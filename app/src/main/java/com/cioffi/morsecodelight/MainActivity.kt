package com.cioffi.morsecodelight

import android.content.Intent
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.cioffi.morsecodelight.databinding.ActivityMainBinding
import com.cioffi.morsecodelight.helpers.CharToMorse

import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var flashLightStatus: Boolean = false
    var stopNow : Boolean = false
    var encodeJob: Job? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList[0]

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        val generateEncode: Button = findViewById(R.id.morsBtn)
        var textToEncode = findViewById(R.id.editTectToEncode) as EditText

        val stopEncode: Button = findViewById(R.id.stopEncod)

        stopEncode.setOnClickListener {
            encodeJob?.cancel()
            torchOff(cameraManager, cameraId)
            Toast.makeText(this@MainActivity, getString(R.string.txt_mors_sig_interrupted), Toast.LENGTH_LONG).show()
        }


        generateEncode.setOnClickListener {
            if(encodeJob ==  null || encodeJob!!.isCancelled) {
                encodeJob = GlobalScope.launch(Dispatchers.Main) {
                    openFlashLight(textToEncode.text.toString())
                }
            }
        }

    }


    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun openFlashLight(phrase: String) {
        phrase.replace("\\s".toRegex(), "/")
        var phChars = phrase.toList()
        //char in phrase
        for (morsChar in phChars) {

            var morsCharList = ""
            try {
                morsCharList = CharToMorse.convertCharToMors(morsChar).toString()
            }catch (illE : IllegalArgumentException ){
                Toast.makeText(this@MainActivity, "${illE.message}", Toast.LENGTH_LONG).show()
                encodeJob?.cancel()
                return
                
            }
            Toast.makeText(this@MainActivity, "Letter $morsChar", Toast.LENGTH_LONG).show()
            
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
                        delay(3000)
                    }
                }
                delay(500)
            }
            //wait 3 sec from on char to another
            delay(1500)
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