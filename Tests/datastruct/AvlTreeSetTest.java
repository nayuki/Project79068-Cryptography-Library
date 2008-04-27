package datastruct;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import p79068.util.Random;
import p79068.datastruct.AvlTreeSet;


public class AvlTreeSetTest {
	
	private static Method checkStructure;
	
	
	@BeforeClass
	public static void setUp() throws NoSuchMethodException {
		checkStructure = AvlTreeSet.class.getDeclaredMethod("checkStructure");
		checkStructure.setAccessible(true);
	}
	
	
	@Test
	public void testEmpty() {
		AvlTreeSet<Integer> avlSet = new AvlTreeSet<Integer>();
		checkStructure(avlSet);
	}
	
	
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
	
	
	
	private static void checkStructure(AvlTreeSet<?> avlSet) {
		try {
			checkStructure.invoke(avlSet);
		} catch (IllegalArgumentException e) {
			throw new AssertionError(e);
		} catch (IllegalAccessException e) {
			throw new AssertionError(e);
		} catch (InvocationTargetException e) {
			throw new AssertionError(e);
		}
	}
	
	
	private static <E extends Comparable<? super E>> void checkConsistency(AvlTreeSet<E> avlSet, Set<E> javaSet) {
		checkStructure(avlSet);
		List<E> list0 = avlSet.dumpInOrder();
		List<E> list1 = new ArrayList<E>();
		list1.addAll(javaSet);
		Collections.sort(list1);
		assertEquals(list0, list1);
	}
	
}