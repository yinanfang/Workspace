package test;

import static org.junit.Assert.*;

import org.junit.Test;
import main.WhenItRainsItPours;

public class WhenItRainsItPoursTest {

	@Test
	public void test() {
		assertEquals(WhenItRainsItPours.answer(new int[]{1, 4, 2, 5, 1, 2, 3}), 5);
		assertEquals(WhenItRainsItPours.answer(new int[]{1, 2, 3, 2, 1}), 0);
	}

}
