package kz.beigam.service

import kz.beigam.model.Bank
import kz.beigam.model.Expression
import kz.beigam.model.Money
import kz.beigam.model.Sum
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MoneyServiceTests {
    private val bank = Bank()

    @Test
    fun testMultiplication() {
        val five = Money.dollar(5)
        assertEquals(Money.dollar(10), five.times(2))
        assertEquals(Money.dollar(15), five.times(3))
    }

    @Test
    fun testFrancMultiplication() {
        val five = Money.franc(5)
        assertEquals(Money.franc(10), five.times(2))
        assertEquals(Money.franc(15), five.times(3))
    }

    @Test
    fun testEquality() {
        assertTrue { Money.dollar(5) == Money.dollar(5) }
        assertFalse { Money.dollar(5) == Money.dollar(6) }
        assertFalse { Money.dollar(5) == Money.franc(6) }
    }

    @Test
    fun testCurrency() {
        assertEquals("USD", Money.dollar(1).currency)
        assertEquals("CHF", Money.franc(1).currency)
    }

    @Test
    fun testSimpleAddition() {
        val five = Money.dollar(5)
        val sum: Expression = five.plus(five)
        val reduced = bank.reduce(sum, "USD")
        assertEquals(Money.dollar(10), reduced)
    }

    @Test
    fun testPlusReturnSum() {
        val five = Money.dollar(5)
        val result: Expression = five.plus(five)
        val sum: Sum = result as Sum
        assertEquals(five, sum.augend)
        assertEquals(five, sum.addend)
    }

    @Test
    fun testReduceSum() {
        val sum: Expression = Sum(Money.dollar(3), Money.dollar(4))
        val result = bank.reduce(sum, "USD")
        assertEquals(Money.dollar(7), result)
    }

    @Test
    fun testReduceMoney() {
        val result = bank.reduce(Money.dollar(1), "USD")
        assertEquals(Money.dollar(1), result)
    }

    @Test
    fun testReduceMoneyOfDifferentCurrency() {
        bank.addRate("CHF","USD", 2)
        val result = bank.reduce(Money.franc(2), "USD")
        assertEquals(Money.dollar(1), result)
    }

    @Test
    fun testIdentityRate() {
        assertEquals(1, bank.rate("USD","USD"))
    }

    @Test
    fun testMixedAddition() {
        val fiveBucks = Money.dollar(5)
        val tenFrancs = Money.franc(10)
        bank.addRate("CHF","USD", 2)
        val result = bank.reduce(fiveBucks.plus(tenFrancs), "USD")
        assertEquals(Money.dollar(10), result)
    }

    @Test
    fun testSumPlusMoney() {
        val fiveBucks = Money.dollar(5)
        val tenFrancs = Money.franc(10)
        bank.addRate("CHF","USD", 2)
        val sum = Sum(fiveBucks, tenFrancs).plus(fiveBucks)
        val result = bank.reduce(sum, "USD")
        assertEquals(Money.dollar(15), result)
    }

    @Test
    fun testSumTimes() {
        val fiveBucks = Money.dollar(5)
        val tenFrancs = Money.franc(10)
        bank.addRate("CHF","USD", 2)
        val sum = Sum(fiveBucks, tenFrancs).times(2)
        val result = bank.reduce(sum, "USD")
        assertEquals(Money.dollar(20), result)
    }

}