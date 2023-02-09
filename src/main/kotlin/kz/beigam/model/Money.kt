package kz.beigam.model


open class Money(
    open var amount: Int,
    open val currency: String
): Expression {
    override fun times(count: Int): Expression {
        return Money(this.amount * count, currency)
    }
    override fun plus(addend: Expression):Expression = Sum(this, addend)

    override fun reduce(bank:Bank, to: String): Money {
        val rate = bank.rate(currency, to)
        return Money(amount/rate, to)
    }
    override fun equals(other: Any?): Boolean {
        val money = other as Money
        return amount == money.amount
                && currency ==money.currency
    }
    override fun toString() = "$amount $currency"
    override fun hashCode(): Int {
        var result = amount
        result = 31 * result + currency.hashCode()
        return result
    }

    companion object {
        fun dollar(amount: Int) = Money(amount, "USD")
        fun franc(amount: Int) = Money(amount, "CHF")
    }
}