package test;

import static org.junit.Assert.*;

import org.junit.Test;

import main.PeculiarBalance;;

public class PeculiarBalanceTest {

	@Test
	public void testAnswer0To9() {
		System.out.println("======================\ntestAnswer0To9");
		int input = 0;
		String[] expected = new String[]{};
		assertArrayEquals(PeculiarBalance.answer(input), expected);
		
		input = 1;
		expected = new String[]{"R"};
		assertArrayEquals(PeculiarBalance.answer(input), expected);
		
		input = 2;
		expected = new String[]{"L", "R"};;
		assertArrayEquals(PeculiarBalance.answer(input), expected);
		
		input = 3;
		expected = new String[]{"-", "R"};;
		assertArrayEquals(PeculiarBalance.answer(input), expected);
		
		input = 4;
		expected = new String[]{"R", "R"};;
		assertArrayEquals(PeculiarBalance.answer(input), expected);
		
		input = 5;
		expected = new String[]{"L", "L", "R"};;
		assertArrayEquals(PeculiarBalance.answer(input), expected);
		
		input = 6;
		expected = new String[]{"-", "L", "R"};;
		assertArrayEquals(PeculiarBalance.answer(input), expected);
		
		input = 7;
		expected = new String[]{"R", "L", "R"};;
		assertArrayEquals(PeculiarBalance.answer(input), expected);
		
		input = 8;
		expected = new String[]{"L", "-", "R"};;
		assertArrayEquals(PeculiarBalance.answer(input), expected);
		
		input = 9;
		expected = new String[]{"-", "-", "R"};;
		assertArrayEquals(PeculiarBalance.answer(input), expected);
	}
	
	@Test
	public void testAnswer10To99() {
		System.out.println("======================\ntestAnswer10To99");
		int input = 10;
		String[] expected = new String[]{"R", "-", "R"};;
		assertArrayEquals(PeculiarBalance.answer(input), expected);
		
		input = 17;
		expected = new String[]{"L", "-", "L", "R"};;
		assertArrayEquals(PeculiarBalance.answer(input), expected);
		
		input = 44;
		expected = new String[]{"L", "-", "L", "L", "R"};;
		assertArrayEquals(PeculiarBalance.answer(input), expected);
		
		input = 55;
		expected = new String[]{"R", "-", "-", "L", "R"};;
		assertArrayEquals(PeculiarBalance.answer(input), expected);
		
		input = 87;
		expected = new String[]{"-", "L", "R", "-", "R"};;
		assertArrayEquals(PeculiarBalance.answer(input), expected);
	}
	
	@Test
	public void testAnswer100To999() {
		System.out.println("======================\ntestAnswer100To999");
		int input = 111;
		String[] expected = new String[]{"-", "R", "-", "R", "R"};;
		assertArrayEquals(PeculiarBalance.answer(input), expected);
		
		input = 321;
		expected = new String[]{"-", "L", "-", "-", "R", "R"};;
		assertArrayEquals(PeculiarBalance.answer(input), expected);
		
		input = 423;
		expected = new String[]{"-", "-", "L", "R", "L", "L", "R"};;
		assertArrayEquals(PeculiarBalance.answer(input), expected);
		
		input = 645;
		expected = new String[]{"-", "L", "-", "-", "L", "-", "R"};;
		assertArrayEquals(PeculiarBalance.answer(input), expected);
		
		input = 796;
		expected = new String[]{"R", "R", "R", "L", "R", "-", "R"};;
		assertArrayEquals(PeculiarBalance.answer(input), expected);
	}
	
	@Test
	public void testAnswerExtreme() {
//		int input = 1000000000;
//		String[] expected = new String[]{"-", "-", "-", "-", "R", "-", "R", "L", "R", "-", "-", "L", "R", "-", "L", "R", "L", "L", "-", "R" };;
//		assertArrayEquals(PeculiarBalance.answer(input), expected);
		
		int input = 500000000;
		String[] expected = new String[]{"-",   "-",   "L",   "L",   "R",   "-",   "L",   "-",   "L",   "L",   "L",   "-",   "L",   "L",   "-",   "L",   "-",   "R",   "R"};;
		assertArrayEquals(PeculiarBalance.answer(input), expected);
		
		input = 103004000;
		expected = new String[]{"-",   "L",   "-",   "R",   "L",   "-",   "R",   "R",   "-",   "R",   "R",   "L",   "L",   "L",   "R",   "R",   "L",   "R"};;
		assertArrayEquals(PeculiarBalance.answer(input), expected);
	}
}
