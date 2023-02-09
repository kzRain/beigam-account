package kz.beigam.model

interface Expression {
    fun plus(addend: Expression): Expression
    fun times(count: Int): Expression
    fun reduce(bank:Bank, to:String): Money
}