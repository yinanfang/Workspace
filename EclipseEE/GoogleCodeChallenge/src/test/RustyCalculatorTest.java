package test;
import static org.junit.Assert.*;
import org.junit.Test;

import main.RustyCalculator;

public class RustyCalculatorTest {

	@Test
	public void testAnswer() {
		assertEquals(RustyCalculator.answer("2+3*2"), "232*+");
		assertEquals(RustyCalculator.answer("2*4*3+9*3+5"), "243**93*5++");
	}


}
