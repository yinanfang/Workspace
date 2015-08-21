package test;

import static org.junit.Assert.*;

import org.junit.Test;

import main.SquareSupplies;

public class SquareSuppliesTest {

	@Test
	public void test() {
		assertEquals(SquareSupplies.answer(1), 1);
		assertEquals(SquareSupplies.answer(7), 4);
		assertEquals(SquareSupplies.answer(24), 3);
		assertEquals(SquareSupplies.answer(160), 2);
		assertEquals(SquareSupplies.answer(999), 4);
		assertEquals(SquareSupplies.answer(1000), 5);
	}

}
