package com.test.myportfolio

fun main(array: Array<String>) {

    val list1 = mutableListOf<Int>()
    val list2 = mutableListOf<Boolean>()

    for ((index, num) in (1..9).withIndex()) {
        list1.add(num)
        list2.add(list1[index] % 2 == 0)
    }
    println(list1)
    println(list2)
    println()

    println(rank(74))
    println()

    println(sum(0))
    println()

    gugu(6)
}

fun gugu(i: Int) {
    for (num in 1..9) {
        println("$i X $num = ${i * num}")
    }
}

fun sum(num: Int): Int {
    var result = 0
    var nums = num

    do {
        result += nums % 10
        nums /= 10
    } while (nums > 0)

    return result
}

fun rank(score: Int): String = when (score) {
    in 90..100 -> "A"
    in 80..89 -> "B"
    in 70..79 -> "C"
    else -> "F"
}
