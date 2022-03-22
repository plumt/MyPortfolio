package com.test.myportfolio.fastcampus

fun main(array: Array<String>) {

    val night: Night = Night("night", 10, 2)
    val monster: Monster = Monster("monster", 5, 3)

    night.attack(monster, night.power)
    monster.attack(night, monster.power)

    print(night)
    print(monster)

    night.attack(monster, night.power)
    monster.attack(night, monster.power)

    print(night)
    print(monster)

    night.attack(monster, night.power)
    monster.attack(night, monster.power)

    print(night)
    print(monster)

    night.attack(monster, night.power)
    monster.attack(night, monster.power)

    print(night)
    print(monster)
}

fun print(character: Character) {
    println("--------------")
    println("${character.name} 체력 : ${character.hp} | 파워 : ${character.power}")
    println("--------------")
}

open class Character(val name: String, var hp: Int, var power: Int) {

    open fun attack(character: Character, power: Int = this.power) {
        if(this.hp > 0) {
            println("${name}의 공격!")
            character.hp -= power
            if (hp > 0) println("${character.name}의 남은 체력 : ${character.hp}")
            else println("${character.name}이 사망했습니다")
        } else{
            println("${this.name}이 사망했습니다")
        }
    }
}

class Monster(name: String, hp: Int, power: Int) : Character(name, hp, power) {
    override fun attack(character: Character, power: Int) {
        super.attack(character, power)
        powerUp()
    }

    private fun powerUp() {
        if (hp > 0) {
            power += 2
            println("${name}의 파워가 상승했습니다. 파워 : ${power}")
        }
    }
}

class Night(name: String, hp: Int, power: Int) : Character(name, hp, power) {
    override fun attack(character: Character, power: Int) {
        super.attack(character, power)
        heal()
    }

    private fun heal() {
        if (hp > 0) {
            hp += 1
            println("${name}이 체력을 회복했습니다. 남은 체력 : ${hp}")
        }
    }
}