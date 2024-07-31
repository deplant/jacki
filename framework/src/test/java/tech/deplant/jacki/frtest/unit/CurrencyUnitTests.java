package tech.deplant.jacki.frtest.unit;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import tech.deplant.jacki.framework.CurrencyUnit;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static tech.deplant.jacki.framework.CurrencyUnit.Coin.*;

/**
 * Tests for currency calculations
 * @see CurrencyUnit
 * @see Coin
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Execution(ExecutionMode.CONCURRENT)
public class CurrencyUnitTests {

	@Test
	public void result_of_all_aliases_should_be_correct() throws Throwable {
		assertEquals(new BigInteger("1000000000000000000"), CurrencyUnit.VALUE(GIGACOIN, "1"));
		assertEquals(CurrencyUnit.VALUE(GIGACOIN, "1"), CurrencyUnit.VALUE(MEGACOIN, "1000"));
		assertEquals(CurrencyUnit.VALUE(MEGACOIN, "1"), CurrencyUnit.VALUE(KILOCOIN, "1000"));
		assertEquals(CurrencyUnit.VALUE(KILOCOIN, "1"), CurrencyUnit.VALUE(COIN, "1000"));
		assertEquals(CurrencyUnit.VALUE(COIN, "1"), CurrencyUnit.VALUE(MILLICOIN, "1000"));
		assertEquals(CurrencyUnit.VALUE(MILLICOIN, "1"), CurrencyUnit.VALUE(MICROCOIN, "1000"));
		assertEquals(CurrencyUnit.VALUE(MICROCOIN, "1"), CurrencyUnit.VALUE(NANOCOIN, "1000"));
		assertEquals(CurrencyUnit.VALUE(NANOCOIN, "1"), BigInteger.ONE);
	}

	@Test
	public void ever_equals_to_custom_token_with_9_digits() throws Throwable {
		assertEquals(CurrencyUnit.VALUE(new CustomToken(9),"1"), CurrencyUnit.VALUE(COIN, "1"));
	}

}
