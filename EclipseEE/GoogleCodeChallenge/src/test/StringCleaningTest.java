package test;

import static org.junit.Assert.*;

import org.junit.Test;
import main.StringCleaning;

public class StringCleaningTest {

	@Test
	public void testCornerCases() {
		assertEquals(StringCleaning.answer(null, null), "");
		assertEquals(StringCleaning.answer("", ""), "");
		assertEquals(StringCleaning.answer("asdfasdf", ""), "asdfasdf");
		assertEquals(StringCleaning.answer("", "asdfasdf"), "");
		assertEquals(StringCleaning.answer("sdf", "asdfasdf"), "sdf");		
	}
	
	@Test
	public void testNormalCases() {
		assertEquals(StringCleaning.answer("lololololo", "lol"), "looo");
		assertEquals(StringCleaning.answer("goodgooogoogfogoood", "goo"), "dogfood");
		assertEquals(StringCleaning.answer("aabb", "ab"), "");
//		assertEquals(StringCleaning.answer("lololololo", "lol"), "looo");
//		assertEquals(StringCleaning.answer("lololololo", "lol"), "looo");
	}

}
