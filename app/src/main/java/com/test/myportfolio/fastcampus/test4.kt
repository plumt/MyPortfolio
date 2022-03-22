package com.test.myportfolio.fastcampus


fun main(args: Array<String>){
    val msg: String = "하잘가세요"
    val temp = msg.toByteArray(charset("euc-kr"))
    println("temp : $temp")
    val size = temp.size / 12
    lateinit var sendMsg: ByteArray
    for(i in 0..size){
        val cmd = ByteArray(20)
        cmd[0] = 0xAA.toByte()
        cmd[1] = 0x9A.toByte()
        cmd[2] = 0x0D.toByte()
        cmd[3] = (i+1).toByte()
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
        for(j in 1 .. 15){
            sum += cmd[j]
        }
        cmd[16] = sum.toByte()
        cmd[17] = 0xA5.toByte()
        cmd[18] = 0x5A.toByte()
        cmd[19] = 0x7E.toByte()
        sendMsg = cmd
        println("$i -> $cmd")
    }

    println(temp)
    println(sendMsg.toString(charset("euc-kr")))
}

fun getCheck(temp: ByteArray, index: Int) : Byte = if(temp.size > index) { temp[index] } else { 0x00.toByte() }

