package com.github.ogino.ipcam_eval

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.niqdev.mjpeg.*

class MainActivity : AppCompatActivity() {

    private val mjpegView : MjpegView by lazy {
        findViewById<MjpegSurfaceView>(R.id.mjpeg_view)
    }
    private val STREAM_URL = "https://172.16.0.253/?action=stream"
    private val TIMEOUT = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun loadIpCam() {
        SSLMjpeg.newInstance()
                .open(STREAM_URL, TIMEOUT)
                .subscribe( {
                    mjpegView.setSource(it)
                    mjpegView.setDisplayMode(DisplayMode.FULLSCREEN)
                },
                        {
                            Log.e("loadIpCam", it.toString());
                            Toast.makeText(this, "Error: " + it.toString(), Toast.LENGTH_LONG).show();
                        })
    }

    override fun onResume() {
        super.onResume()
        loadIpCam()
    }
}