package test;

import static org.junit.Assert.*;

import org.junit.Test;

import main.ZombitMonitoring;;

public class ZombitMonitoringTest {

	@Test
	public void test() {
		// No need for merge
		int[][] input = new int[][]{{1, 3}, {3, 6}};
		assertEquals(ZombitMonitoring.answer(input), 5);
		
		input = new int[][]{{10, 14}, {4, 18}, {19, 20}, {13, 20}};
		assertEquals(ZombitMonitoring.answer(input), 16);
		
		// Need merge
		input = new int[][]{{1, 3}, {3, 5}, {6, 9}};
		assertEquals(ZombitMonitoring.answer(input), 7);
		
		input = new int[][]{{11, 13}, {0, 5}, {8, 9}, {8, 20}};
		assertEquals(ZombitMonitoring.answer(input), 17);
		
	}

}
