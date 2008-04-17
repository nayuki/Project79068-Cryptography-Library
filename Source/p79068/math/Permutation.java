package p79068.math;


/**
Permutes a sequence to the lexicographically next permutation.
<p>Instantiability: <em>Not applicable</em></p>
<p>For a list of length <var>n</var>, the time complexity of computing the next iteration is O(<var>n</var>), so the complexity of iterating over all permutations is O(<var>n</var> &middot; <var>n</var>!).</p>
<p>Usage example:</p>
<p><code>int[] state = {1, 6, 1, 8, 0};<br>
do {<br>
&nbsp;&nbsp;<i>doSomethingWith</i>(state);<br>
}<br>
while(Permutation.next(state));</code></p>
*/
public class Permutation {
	
	/*
	Algorithm details:
	 To generate the next permutation, find the longest non-strictly decreasing suffix. Reverse this suffix. Let x be the item directly in front of the suffix. Find the item that is greater than x and closest to the beginning of the suffix. Swap it with x.
	 In this implementation, i is the index such that a[i] breaks the decreasing sequence following it (i.e. {a[i+1], ..., a[length-1]}). e.g. in {1,2,3,6,5,4}, i = 2.
	*/
	public static boolean next(char[] a) {
		int i, n = a.length;
		for (i = n - 2;; i--) {
			if (i < 0)
				return false;
			if (a[i] < a[i + 1])
				break;
		}
		for (int j = 1; i + j < n - j; j++) {
			char tp = a[i + j];
			a[i + j] = a[n - j];
			a[n - j] = tp;
		}
		int j;
		for (j = i + 1; a[j] <= a[i]; j++);
		char tp = a[i];
		a[i] = a[j];
		a[j] = tp;
		return true;
	}
	
	public static boolean next(int[] a) {
		int i, n = a.length;
		for (i = n - 2;; i--) {
			if (i < 0)
				return false;
			if (a[i] < a[i + 1])
				break;
		}
		for (int j = 1; i + j < n - j; j++) {
			int tp = a[i + j];
			a[i + j] = a[n - j];
			a[n - j] = tp;
		}
		int j;
		for (j = i + 1; a[j] <= a[i]; j++);
		int tp = a[i];
		a[i] = a[j];
		a[j] = tp;
		return true;
	}
	
	public static boolean next(long[] a) {
		int i, n = a.length;
		for (i = n - 2;; i--) {
			if (i < 0)
				return false;
			if (a[i] < a[i + 1])
				break;
		}
		for (int j = 1; i + j < n - j; j++) {
			long tp = a[i + j];
			a[i + j] = a[n - j];
			a[n - j] = tp;
		}
		int j;
		for (j = i + 1; a[j] <= a[i]; j++);
		long tp = a[i];
		a[i] = a[j];
		a[j] = tp;
		return true;
	}
	
	public static boolean next(float[] a) {
		int i, n = a.length;
		for (i = n - 2;; i--) {
			if (i < 0)
				return false;
			if (a[i] < a[i + 1])
				break;
		}
		for (int j = 1; i + j < n - j; j++) {
			float tp = a[i + j];
			a[i + j] = a[n - j];
			a[n - j] = tp;
		}
		int j;
		for (j = i + 1; a[j] <= a[i]; j++);
		float tp = a[i];
		a[i] = a[j];
		a[j] = tp;
		return true;
	}
	
	public static boolean next(double[] a) {
		int i, n = a.length;
		for (i = n - 2;; i--) {
			if (i < 0)
				return false;
			if (a[i] < a[i + 1])
				break;
		}
		for (int j = 1; i + j < n - j; j++) {
			double tp = a[i + j];
			a[i + j] = a[n - j];
			a[n - j] = tp;
		}
		int j;
		for (j = i + 1; a[j] <= a[i]; j++);
		double tp = a[i];
		a[i] = a[j];
		a[j] = tp;
		return true;
	}
	
	public static boolean next(Comparable[] a) {
		int i, n = a.length;
		for (i = n - 2;; i--) {
			if (i < 0)
				return false;
			if (a[i].compareTo(a[i + 1]) < 0)
				break;
		}
		for (int j = 1; i + j < n - j; j++) {
			Comparable tp = a[i + j];
			a[i + j] = a[n - j];
			a[n - j] = tp;
		}
		int j;
		for (j = i + 1; a[j].compareTo(a[i]) <= 0; j++);
		Comparable tp = a[i];
		a[i] = a[j];
		a[j] = tp;
		return true;
	}
	
	
	private Permutation() {}
}