package geneticalgorithms;

import java.util.Random;

public abstract class Utility {
	private static Random r = new Random();
	
	public static Random rand() {
		return r;
	}
	
    public static <T extends Comparable<? super T>> T max(T a, T b) {
        if (a == null) {
            if (b == null) return a;
            else return b;
        }
        if (b == null)
            return a;
        return a.compareTo(b) > 0 ? a : b;
    }
}
