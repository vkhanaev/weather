package com.example.weather.experiments

import android.os.Looper

class MyThread : Thread() {
    override fun run() {
        Looper.prepare()
        Looper.loop()
    }
}
