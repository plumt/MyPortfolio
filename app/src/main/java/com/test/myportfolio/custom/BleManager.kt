package com.test.myportfolio.custom

import android.content.Context
import android.util.Log
import com.polidea.rxandroidble2.RxBleClient
import com.polidea.rxandroidble2.RxBleConnection
import com.polidea.rxandroidble2.RxBleDevice
import com.polidea.rxandroidble2.scan.ScanFilter
import com.polidea.rxandroidble2.scan.ScanResult
import com.polidea.rxandroidble2.scan.ScanSettings
import io.reactivex.disposables.Disposable
import java.util.*
import javax.inject.Singleton


@Singleton
class BleManager(context: Context) {

    var rxBleClient: RxBleClient = RxBleClient.create(context)

    var mDevice: RxBleDevice? = null

    var mRxBleConnection: RxBleConnection? = null

    var connectDisposable: Disposable? = null

    var scanSubscription: Disposable? = null

    interface ScanDeviceCallback {
        fun onScanSuccess(scanResult: ScanResult)
        fun onScanFail(throwable: Throwable)
    }

    interface ConnectDeviceCallback {
        fun onConnectSuccess(rxBleConnection: RxBleConnection)
        fun onConnectFail(throwable: Throwable)
    }

    interface WriteDeviceCallback {
        fun onWriteSuccess(write: ByteArray)
        fun onWriteFail(throwable: Throwable)
    }

    /**
     * 블루투스 디바이스 검색.
     */



    fun connectDevice(macAddress: String, connectDeviceCallback: ConnectDeviceCallback?) {
        Log.i("sjh", "macAddress : ${macAddress}")
        mDevice = rxBleClient.getBleDevice(macAddress)
        connectDisposable =
            mDevice?.establishConnection(false) // <-- autoConnect flag
                ?.subscribe(
                    { rxBleConnection ->
                        mRxBleConnection = rxBleConnection
                        connectDeviceCallback?.onConnectSuccess(rxBleConnection)
                    }
                ) { throwable ->

                    throwable?.printStackTrace()
                    connectDeviceCallback?.onConnectFail(throwable)
                }
    }

    fun writeDevice(
        rxBleConnection: RxBleConnection,
        characteristicUUID: UUID,
        bytesToWrite: ByteArray,
        writeDeviceCallback: WriteDeviceCallback?
    ) {
        rxBleConnection.writeCharacteristic(characteristicUUID, bytesToWrite).subscribe({
            writeDeviceCallback?.onWriteSuccess(it)
        }, {
            writeDeviceCallback?.onWriteFail(it)
        })
    }
}