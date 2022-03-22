package com.test.myportfolio.fastcampus

fun main(array: Array<String>){

    val first: First = First()
    first.plus(10,20)
    first.minus(10,20)
    first.div(10,20)
    first.mul(10,20)

    println()

    val second: Second = Second("s","950703")
    second.print()
    second.setMoney(10000)
    second.print()
    second.getMoney(2800)
    second.print()

    println()

    val third: Third = Third()
    third.turnOn()
    third.nextChangel()
    third.nextChangel()
    third.preChangel()
    third.turnOff()



}

class First{

    fun plus(num1: Int, num2: Int){
        println("$num1 + $num2 = ${num1+num2}")
    }

    fun minus(num1: Int, num2: Int){
        println("$num1 - $num2 = ${num1-num2}")
    }

    fun div(num1: Int, num2: Int){
        println("$num1 / $num2 = ${num1/num2}")
    }

    fun mul(num1: Int, num2: Int){
        println("$num1 * $num2 = ${num1*num2}")
    }
}

class Second{
    var name: String
    var birth: String
    private var money = 0

    constructor(name: String, birth: String){
        this.name = name
        this.birth = birth
    }

    fun print(){
        println("${this.name}님의 남은 잔액 : ${money}원")
    }
    fun setMoney(money: Int){
        if(money > 0) {
            println("임금 ${money}원")
            this.money += money
        }
    }
    fun getMoney(money: Int){
        if(this.money > money) {
            println("출금 ${money}원")
            this.money -= money
        }
    }
}

class Third{
    private var chanel = 0
    private val chanelList = charArrayOf('S','M','K')

    private var switch = false

    fun turnOn(){
        if(!switch){
            println("TV전원 온")
        }
        switch = true
    }

    fun turnOff(){
        if(switch){
            println("TV전원 오프")
        }
        switch = false
    }

    fun nextChangel(){
        if(switch) {
            chanel++
            if (chanel > 2) chanel = 0
            println("chanel : ${chanelList[chanel]}")
        }
    }

    fun preChangel(){
        if(switch) {
            chanel--
            if (chanel < 0) chanel = 2
            println("chanel : ${chanelList[chanel]}")
        }
    }
}