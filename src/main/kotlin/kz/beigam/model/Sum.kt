package kz.beigam.model

class Sum(
    val augend: Expression,
    val addend: Expression
) : Expression {
    override fun plus(addend: Expression):Expression {
        return Sum(this, addend)
    }

    override fun times(count: Int): Expression {
        return Sum(augend.times(count), addend.times(count))
    }


    override fun reduce(bank:Bank, to: String): Money {
        val amount = augend.reduce(bank,to).amount + addend.reduce(bank,to).amount
        return Money(amount, to)
    }
}