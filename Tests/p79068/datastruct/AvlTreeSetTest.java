package p79068.datastruct;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import p79068.util.Random;


public final class AvlTreeSetTest {
	
	@Test
	public void testEmpty() {
		AvlTreeSet<Integer> set = new AvlTreeSet<Integer>();
		set.checkStructure();
	}
	
	
	@Test
	public void testAdd() {
		AvlTreeSet<Integer> set = new AvlTreeSet<Integer>();
		set.add( 1);
		set.add( 4);
		set.add( 9);
		set.add(16);
		set.add(25);
		set.add(36);
		set.add(49);
		set.checkStructure();
		assertEquals(7, set.size());
		assertTrue(set.contains( 1));
		assertTrue(set.contains( 4));
		assertTrue(set.contains( 9));
		assertTrue(set.contains(16));
		assertTrue(set.contains(25));
		assertTrue(set.contains(36));
		assertTrue(set.contains(49));
		assertFalse(set.contains(-99));
		assertFalse(set.contains(-21));
		assertFalse(set.contains(  0));
		assertFalse(set.contains(  2));
		assertFalse(set.contains(  6));
		assertFalse(set.contains( 37));
		assertFalse(set.contains( 50));
	}
	
	
	@Test
	public void testAddWithDuplicates() {
		AvlTreeSet<String> set = new AvlTreeSet<String>();
		set.add("Bob");
		set.add("Carol");
		set.add("Alice");
		set.add("Dave");
		set.add("Alice");
		set.add("Bob");
		set.add("Alice");
		set.checkStructure();
		assertEquals(4, set.size());
		assertTrue(set.contains("Alice"));
		assertTrue(set.contains("Bob"));
		assertTrue(set.contains("Carol"));
		assertTrue(set.contains("Dave"));
		assertFalse(set.contains(""));
		assertFalse(set.contains("Eve"));
		assertFalse(set.contains("Mallory"));
		assertFalse(set.contains("Peggy"));
		assertFalse(set.contains("Trent"));
		assertFalse(set.contains("Victor"));
		assertFalse(set.contains("Zeta"));
	}
	
	
	@Test
	public void testAddRandomly() {
		for (int i = 0; i < 30; i++) {
			AvlTreeSet<Integer> avlSet = new AvlTreeSet<Integer>();
			Set<Integer> javaSet = new HashSet<Integer>();
			
			for (int j = 0; j < 300; j++) {
				int x = Random.DEFAULT.randomInt(1000);
				avlSet.add(x);
				javaSet.add(x);
			}
			checkConsistency(avlSet, javaSet);
		}
	}
	
	
	@Test
	public void testRemoveRandomly() {
		for (int i = 0; i < 30; i++) {
			AvlTreeSet<Integer> avlSet = new AvlTreeSet<Integer>();
			Set<Integer> javaSet = new HashSet<Integer>();
			
			for (int j = 0; j < 300; j++) {
				int x = Random.DEFAULT.randomInt(1000);
				avlSet.add(x);
				javaSet.add(x);
			}
			checkConsistency(avlSet, javaSet);
			
			for (int j = 0; j < 100; j++) {
				int x = Random.DEFAULT.randomInt(1000);
				avlSet.remove(x);
				javaSet.remove(x);
			}
			checkConsistency(avlSet, javaSet);
		}
	}
	
	
	private static <E extends Comparable<? super E>> void checkConsistency(AvlTreeSet<E> avlSet, Set<E> javaSet) {
		avlSet.checkStructure();
		List<E> list0 = avlSet.dumpInOrder();
		List<E> list1 = new ArrayList<E>();
		list1.addAll(javaSet);
		Collections.sort(list1);
		assertEquals(list0, list1);
	}
	
}