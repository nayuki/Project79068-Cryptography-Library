package p79068.datastruct;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.HashSet;
import java.util.TreeSet;
import org.junit.Test;
import p79068.util.Random;


public class BTreeSetTest {
	
	@Test
	public void testEmpty() {
		BTreeSet<Integer> s = new BTreeSet<Integer>(2);
		assertEquals(0, s.size());
		assertFalse(s.contains(0));
		assertFalse(s.contains(1));
		assertFalse(s.contains(200));
	}
	
	
	@Test
	public void testInsert0() {
		BTreeSet<Integer> s = new BTreeSet<Integer>(2);
		s.insert(2);
		//assertEquals(1, s.size());
		assertTrue(s.contains(2));
		assertFalse(s.contains(0));
	}
	
	
	@Test
	public void testInsert1() {
		BTreeSet<Integer> s = new BTreeSet<Integer>(2);
		s.insert(1);
		s.insert(3);
		//assertEquals(2, s.size());
		assertTrue(s.contains(1));
		assertTrue(s.contains(3));
		assertFalse(s.contains(0));
		assertFalse(s.contains(2));
	}
	
	
	@Test
	public void testInsert2() {
		BTreeSet<Integer> s = new BTreeSet<Integer>(2);
		s.insert(0);
		s.insert(2);
		s.insert(1);
		//assertEquals(3, s.size());
		assertTrue(s.contains(0));
		assertTrue(s.contains(1));
		assertTrue(s.contains(2));
		assertFalse(s.contains(-1));
		assertFalse(s.contains(3));
	}
	
	
	@Test
	public void testInsertRandomly() {
		for (int i = 0; i < 300; i++) {
			int maxKeys = (Random.DEFAULT.randomInt(5) + 1) * 2;
			HashSet<Integer> set0 = new HashSet<Integer>();
			BTreeSet<Integer> set1 = new BTreeSet<Integer>(maxKeys);
			for (int j = 0; j < 1000; j++) {
				//assertEquals(set0.size(), set1.size());
				int temp = Random.DEFAULT.randomInt(3000);
				set0.add(temp);
				set1.insert(temp);
				//System.out.println("+"+temp);
				//set1.print();
				//System.out.println("---");
			}
			for (int j = 0; j < 300; j++) {
				int temp = Random.DEFAULT.randomInt(3000);
				assertEquals(set0.contains(temp), set1.contains(temp));
			}
		}
	}
	
	
	static <E> void print(java.util.Set<E> s) {
		for (E obj : new TreeSet<E>(s))
			System.out.print(obj+" ");
		System.out.println();
	}
	
}