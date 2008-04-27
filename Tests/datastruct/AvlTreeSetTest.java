package datastruct;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import p79068.util.Random;
import p79068.datastruct.AvlTreeSet;


public class AvlTreeSetTest {
	
	@Test
	public void testAddRandomly() {
		AvlTreeSet<Integer> avlSet = new AvlTreeSet<Integer>();
		Set<Integer> javaSet = new HashSet<Integer>();
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 30000; j++) {
				int x = Random.DEFAULT.randomInt();
				avlSet.add(x);
				javaSet.add(x);
			}
		}
		checkConsistency(avlSet, javaSet);
	}
	
	
	@Test
	public void testRemoveRandomly() {
		AvlTreeSet<Integer> avlSet = new AvlTreeSet<Integer>();
		Set<Integer> javaSet = new HashSet<Integer>();
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 1000; j++) {
				int x = Random.DEFAULT.randomInt(10000);
				avlSet.add(x);
				javaSet.add(x);
			}
			checkConsistency(avlSet, javaSet);
			for (int j = 0; j < 1000; j++) {
				int x = Random.DEFAULT.randomInt(10000);
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