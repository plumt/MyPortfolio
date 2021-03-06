package com.test.myportfolio

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.test.myportfolio.data.model.BleMqttModel
import com.test.myportfolio.databinding.ActivityMainBinding
import com.test.myportfolio.util.PreferenceManager
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import org.json.JSONObject
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    val sharedPreferences: PreferenceManager by inject()

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    lateinit var mqttAndroidClient: MqttAndroidClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        binding.main = mainViewModel

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)


        token()




        mainViewModel.newData.observe(this,{
            if(!it){
                mainViewModel.mqttData.value?.band?.clear()
            }
        })


        mqttAndroidClient = MqttAndroidClient(
            this,
            "tcp://221.139.14.153:1883",
            MqttClient.generateClientId()
        )
        mqttAndroidClient.setCallback(object : MqttCallback {

            override fun connectionLost(cause: Throwable?) {

            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {

//                if(mainViewModel.newData.value == false) {
//                    val id: ArrayList<String> = arrayListOf()
//                    for (i in 0 until JSONObject(message.toString()).getJSONArray("id").length()) {
//                        id.add(JSONObject(message.toString()).getJSONArray("id")[i].toString())
//                        Log.d(
//                            "lys",
//                            "id : $i  ->  ${JSONObject(message.toString()).getJSONArray("id")}"
//                        )
//                    }
//
//                    mainViewModel.mqttData.value = BleMqttModel(
//                        JSONObject(message.toString()).getString("msg"),
//                        id
//                    )
//                    mainViewModel.newData.value = true
//                }
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {

            }

        })
        // 2?????? ???????????? : ???????????? ip ?????? , 3?????? ???????????? : client ??? id??? ????????? ???????????? paho ??? ???????????? id??? ??????????????????

        // 2?????? ???????????? : ???????????? ip ?????? , 3?????? ???????????? : client ??? id??? ????????? ???????????? paho ??? ???????????? id??? ??????????????????
        try {
            val token: IMqttToken =
                mqttAndroidClient.connect(getMqttConnectionOption()) //mqtttoken ??????????????? ????????? connect option??? ?????????
            token.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    mqttAndroidClient.setBufferOpts(getDisconnectedBufferOptions()) //????????? ???????????????
                    Log.e("Connect_success", "Success")
                    try {
                        mqttAndroidClient.subscribe("/openit/digitalTwin/teacher001/event", 0)
                    } catch (e: MqttException) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(
                    asyncActionToken: IMqttToken,
                    exception: Throwable
                ) {   //????????? ???????????????
                    Log.e("connect_fail", "Failure $exception")
                }
            }
        } catch (e: MqttException) {
            e.printStackTrace()
        }

        /*
        *   subscribe ?????? 3?????? ??????????????? ???????????? ???????????? ?????????????????????
        * */


    }


    fun token() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("lys", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            Log.i("lys", "token : $token")
            sharedPreferences.setString(baseContext, "token", token)

        })
    }


    private fun getDisconnectedBufferOptions(): DisconnectedBufferOptions? {
        val disconnectedBufferOptions = DisconnectedBufferOptions()
        disconnectedBufferOptions.isBufferEnabled = true
        disconnectedBufferOptions.bufferSize = 100
        disconnectedBufferOptions.isPersistBuffer = true
        disconnectedBufferOptions.isDeleteOldestMessages = false
        return disconnectedBufferOptions
    }

    private fun getMqttConnectionOption(): MqttConnectOptions? {

        return MqttConnectOptions().apply {
            userName = "openit"
            password = "openit".toCharArray()
            isCleanSession = true
            isAutomaticReconnect = true
        }
    }

    override fun onBackPressed() {
        if (mainViewModel.finish) {
            finish()
        }
        super.onBackPressed()
    }
}