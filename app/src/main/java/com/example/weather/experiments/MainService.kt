package com.example.weather.experiments

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

private const val TAG = "MainServiceTAG"
const val MAIN_SERVICE_STRING_EXTRA = "MainServiceExtra"
const val MAIN_SERVICE_INT_EXTRA = "MainServiceIntExtra"

class MainService(name: String = "MainService") :  IntentService(name) {
    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            sendBack(it.getIntExtra(MAIN_SERVICE_INT_EXTRA, 0).toString())
        }

        createLogMessage("onHandleIntent ${intent?.getStringExtra(MAIN_SERVICE_STRING_EXTRA)}")
    }
    override fun onCreate() {
        createLogMessage("onCreate")
        super.onCreate()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createLogMessage("onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }
    override fun onDestroy() {
        createLogMessage("onDestroy")
        super.onDestroy()
    }
    //Выводим уведомление в строке состояния
    private fun createLogMessage(message: String) {
        Log.d(TAG, message)
    }

    //Отправка уведомления о завершении сервиса
    private fun sendBack(result: String) {
        val broadcastIntent = Intent(TEST_BROADCAST_INTENT_FILTER)
        broadcastIntent.putExtra(THREADS_FRAGMENT_BROADCAST_EXTRA, result)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }
}
