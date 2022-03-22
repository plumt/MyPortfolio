package com.test.myportfolio.ui.main.bluetooth

import android.bluetooth.BluetoothGattService
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.media.MediaDrm
import android.os.Bundle
import android.os.Looper
import android.util.Log
import kotlin.concurrent.timer
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.location.*
import com.test.myportfolio.R
import com.test.myportfolio.BR
import com.test.myportfolio.base.BaseBindingFragment
import com.test.myportfolio.base.BaseRecyclerAdapter
import com.test.myportfolio.data.model.BluetoothModel
import com.test.myportfolio.databinding.FragmentBluetoothBinding
import com.test.myportfolio.databinding.ItemBluetoothBinding
import com.polidea.rxandroidble2.RxBleClient
import com.polidea.rxandroidble2.RxBleConnection
import com.polidea.rxandroidble2.RxBleDevice
import com.polidea.rxandroidble2.RxBleDeviceServices
import com.polidea.rxandroidble2.scan.ScanFilter
import com.polidea.rxandroidble2.scan.ScanResult
import com.test.myportfolio.custom.BleManager
import io.reactivex.disposables.Disposable
import io.reactivex.rxjava3.core.Observable
import org.joda.time.DateTime
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.IOException
import java.nio.charset.Charset
import java.util.*
import java.util.jar.Manifest
import kotlin.math.pow

class BluetoothFragment :
    BaseBindingFragment<FragmentBluetoothBinding, BluetoothViewModel>(BluetoothViewModel::class.java) {
    override val viewModel: BluetoothViewModel by viewModel()
    override fun getResourceId(): Int = R.layout.fragment_bluetooth
    override fun initData(): Boolean = true
    override fun setVariable(): Int = BR.bluetooth
    override fun onBackEvent() {}

    val REQUIRED_PERMISSIONS = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val PERMISSIONS_REQUEST_CODE = 100

    var locatioNManager: LocationManager? = null

    var firstLocation: Location? = null

    lateinit var fusedLocationClient: FusedLocationProviderClient
    val uid = "d973f2e2-b19e-11e2-9e96-0800200c9a66"

    //    private lateinit var connectionStateDisposable: Disposable
    private var mConnectSubscription: Disposable? = null
    var rxBleConnection: RxBleConnection? = null
    var rxBleDeviceServices: RxBleDeviceServices? = null
    var bleGattServices: List<BluetoothGattService>? = null
    private var mWriteSubscription: Disposable? = null
    private var mNotificationSubscription: Disposable? = null
    private lateinit var connectionStateDisposable: Disposable

    private var scanResult: ArrayList<RxBleDevice>? = ArrayList()
    var index = 0
    var str = "aaaa"
    var bandIndex = 0
    lateinit var bleManger: BleManager

    var locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            p0?.let {
                for ((i, location) in it.locations.withIndex()) {
                    viewModel.longitude = location.longitude.toString()
                    viewModel.latitude = location.latitude.toString()
                }

            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bleManger = BleManager(requireContext())
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
//        locatioNManager =
//            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager?

        UUID(-0x121074568629b532L, -0x5c37d8232ae2de13L).apply {
            val widevineId = MediaDrm(this).getPropertyByteArray(MediaDrm.PROPERTY_DEVICE_UNIQUE_ID)
            val encodedWidevineId = Base64.getEncoder().encodeToString(widevineId).trim()
            viewModel.uuid = encodedWidevineId
            MediaDrm(this).close()
        }

        viewModel.rxBleClient = RxBleClient.create(requireContext())
//        viewModel.btnTitle.observe(viewLifecycleOwner, {
//            if (it == "ON") {
//                bleScan()
//            } else {
//                viewModel.scanDisposable?.dispose()
//                viewModel.scanDisposable = null
//            }
//        })

        binding.btnDb.setOnClickListener {
            viewModel.dataBase()
        }


        sharedViewModel.newData.observe(viewLifecycleOwner, {
            if (it) {
                Log.d(
                    "lys",
                    "sharedViewModel.newData.observe -> ${sharedViewModel.mqttData.value!!.band}  ${sharedViewModel.mqttData.value!!.msg}"
                )

                sharedViewModel.mqttData.value!!.apply {
                    str = this.msg

                    scanResult = arrayListOf()

                    write(band)

                    this.band.forEach { bandId ->
                        viewModel.bluetoothItems.value!!.forEachIndexed { index, bluetoothModel ->
                            if (bandId == bluetoothModel.mac) {
                                bluetoothModel.msg = this.msg
                                binding.rvBluetooth.adapter!!.notifyItemChanged(index)
                            }
                        }
                    }
                }
            }
        })

        binding.rvBluetooth.apply {
            adapter = object : BaseRecyclerAdapter.Create<BluetoothModel, ItemBluetoothBinding>(
                R.layout.item_bluetooth,
                holdLayoutResId = R.layout.item_bluetooth,
                bindingVariableId = BR.itemBluetooth,
                bindingListener = BR.bluetoothItemListener
            ) {
                override fun onItemClick(item: BluetoothModel, view: View) {

                }
            }
        }

        binding.btnScan.setOnClickListener {
            viewModel.index = 0
            val permission1 = ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            val permission2 = ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            if (permission1 == PackageManager.PERMISSION_DENIED ||
                permission2 == PackageManager.PERMISSION_DENIED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    10
                )
            } else {
                viewModel.bluetoothItems.clear(true)
                viewModel.scanDisposable?.dispose()
                viewModel.scanDisposable = null
                bleScan()
            }
        }

        binding.tvCount.setOnClickListener {
//            write()
        }

        binding.btnScan.performClick()

        fusedLocationClient.lastLocation.addOnSuccessListener {
            Log.i("lys", "위도1 : ${it.latitude}, 경도1: ${it.longitude}")
        }

        viewModel.rxBleClient = RxBleClient.create(requireContext())
    }


    fun write(band: ArrayList<String>) {
        if(bandIndex==band.size)
            return

        bleManger.connectDevice(band[bandIndex], object : BleManager.ConnectDeviceCallback {
            override fun onConnectSuccess(rxBleConnection: RxBleConnection) {
                bleManger.writeDevice(rxBleConnection, UUID.fromString(uid), smsStartReq(
                    str.toByteArray(
                        Charset.forName("euc-kr")
                    )
                ), object : BleManager.WriteDeviceCallback {
                    override fun onWriteSuccess(write: ByteArray) {

                        write2(band,rxBleConnection)


                    }

                    override fun onWriteFail(throwable: Throwable) {
                    }

                })
            }

            override fun onConnectFail(throwable: Throwable) {
//                write(band)
                sharedViewModel.newData.postValue(false)
            }

        })
    }

    fun write2(band: ArrayList<String>,rxBleConnection: RxBleConnection) {
        deviceWriteMsg(str).forEach {
            bleManger.writeDevice(
                rxBleConnection,
                UUID.fromString(uid),
                it,
                object : BleManager.WriteDeviceCallback {
                    override fun onWriteSuccess(write: ByteArray) {


                        if (bandIndex < band.size) {
                            bleManger.connectDisposable?.dispose()
                            sharedViewModel.newData.postValue(false)
                            return
                        }
                        bandIndex++
                        write(band)
                    }

                    override fun onWriteFail(throwable: Throwable) {

                    }

                })
        }
           }

    private fun smsStartReq(datas: ByteArray): ByteArray =

        ByteArray(9).apply {
            this[0] = 0xAA.toByte()
            this[1] = 0x98.toByte()
            this[2] = 0x02.toByte()
            this[3] =
                datas.size.toByte()
            this[4] =
                if (datas.size % 12 != 0) {
                    ((datas.size / 12) + 1).toByte()
                } else {
                    (datas.size / 12).toByte()
                }
            this[5] =
                (this[1] + this[2] + this[3] + this[4]).toByte()
            this[6] = 0xA5.toByte()
            this[7] = 0x5A.toByte()
            this[8] = 0x7E.toByte()
        }

    fun deviceWriteMsg(msg: String): MutableList<ByteArray> {

        val temp = msg.toByteArray(Charset.forName("euc-kr"))
        println("temp : ${temp.size}")
        val size = temp.size / 12
        var sendMsg: MutableList<ByteArray> = mutableListOf()

        for (i in 0..size) {
            var cmd = ByteArray(20)
            cmd[0] = 0xAA.toByte()
            cmd[1] = 0x9A.toByte()
            cmd[2] = 0x0D.toByte()
            cmd[3] = (i + 1).toByte() //패킷넘버
            cmd[4] = getCheck(temp, 0 + (12 * i))
            cmd[5] = getCheck(temp, 1 + (12 * i))
            cmd[6] = getCheck(temp, 2 + (12 * i))
            cmd[7] = getCheck(temp, 3 + (12 * i))
            cmd[8] = getCheck(temp, 4 + (12 * i))
            cmd[9] = getCheck(temp, 5 + (12 * i))
            cmd[10] = getCheck(temp, 6 + (12 * i))
            cmd[11] = getCheck(temp, 7 + (12 * i))
            cmd[12] = getCheck(temp, 8 + (12 * i))
            cmd[13] = getCheck(temp, 9 + (12 * i))
            cmd[14] = getCheck(temp, 10 + (12 * i))
            cmd[15] = getCheck(temp, 11 + (12 * i))
            var sum = 0
            for (j in 1..15) {
                sum += cmd[j]
            }
            cmd[16] = sum.toByte()
            cmd[17] = 0xA5.toByte()
            cmd[18] = 0x5A.toByte()
            cmd[19] = 0x7E.toByte()
            sendMsg.add(cmd)
            println("$i -> $cmd")
        }



        println(temp)



        return sendMsg
    }

    fun getCheck(temp: ByteArray, index: Int): Byte = if (temp.size > index) {
        temp[index]
    } else {
        0x00.toByte()
    }

    private fun bleScan() {
        if (viewModel.scanDisposable == null) {
            viewModel.scanDisposable = viewModel.rxBleClient.scanBleDevices(
                com.polidea.rxandroidble2.scan.ScanSettings.Builder()
                    .setScanMode(com.polidea.rxandroidble2.scan.ScanSettings.SCAN_MODE_LOW_LATENCY)
                    .build(),
                *(viewModel.scanFilter.toArray(arrayOfNulls<ScanFilter>(viewModel.scanFilter.size)))
            ).subscribe({
                if (viewModel.latitude == "") {
                    locationUpdate()
                }
                addItem(it)
            }, {
                Log.e("lys", "error : ${it.message}")
            })
        }
    }

    private fun locationUpdate() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        fusedLocationClient.requestLocationUpdates(
            LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 1000
            }, locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun addItem(scanResult: ScanResult) {

        viewModel.run {

            bluetoothItems.value!!.forEachIndexed { index, bluetoothModel ->
                if (scanResult.bleDevice.macAddress == bluetoothModel.mac) {
                    bluetoothItems.value!![index].apply {
                        this.rssi = scanResult.rssi.toString()
                        this.time = DateTime.now().toString()
                        this.latitude = viewModel.latitude
                        this.longitude = viewModel.longitude
                        this.heart = scanResult.scanRecord.bytes[11].toString()
                        this.step = scanResult.scanRecord.bytes[13].toString()
                        this.wear = scanResult.scanRecord.bytes[19].toString()
                    }
                    binding.rvBluetooth.adapter!!.notifyItemChanged(index)
                    return
                }
            }

            bluetoothItems.add(
                BluetoothModel(
                    index++,
                    0,
                    uuid,
                    scanResult.bleDevice.macAddress,
                    DateTime.now().toString(),
                    scanResult.scanRecord.bytes[11].toString(),
                    scanResult.scanRecord.bytes[13].toString(),
                    scanResult.scanRecord.bytes[19].toString(),
                    scanResult.rssi.toString(),
                    scanResult.bleDevice.name ?: "이름없음",
                    latitude,
                    longitude
                )
            )


        }
        // 11 - 심박수
        // 13 - 걸음수
        // 19 - 착용여부
        // "$uuid|${it.bleDevice.macAddress}|${DateTime.now()}|${it.scanRecord.bytes[11]}|${it.scanRecord.bytes[13]}|${it.scanRecord.bytes[19]}|${it.rssi}|b"

//        val timer = timer(period = 1000){
//
//        }
//        val location = getLocation()
//        Log.d("lys","location : ${location.first}  ${location.second}")

//        getLocation()
    }

    override fun onDestroy() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        super.onDestroy()
    }
}