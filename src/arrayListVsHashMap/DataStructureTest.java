package arrayListVsHashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DataStructureTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<Integer, Obj> h = new HashMap<Integer, Obj>();
		ArrayList<Obj> al = new ArrayList<Obj>();
		for (int i = 0; i < 1000; i++) {
			h.put(i, new Obj(i, "" + i));
			al.add(new Obj(i, "" + i));
		}
		System.out.println("Iteration measurement:");

		long startTime = System.nanoTime();
		Iterator<Integer> it = h.keySet().iterator();
		while (it.hasNext()) {
			h.get(it.next());
		}
		System.out.println((System.nanoTime() - startTime) + " iterator");

		startTime = System.nanoTime();
		ArrayList<Obj> a = new ArrayList<Obj>(h.values());
		int size = a.size();
		for (int j = 0; j < size; j++) {
			a.get(j);
		}
		System.out
				.println((System.nanoTime() - startTime) + " alist converted");

		startTime = System.nanoTime();
		int size2 = al.size();
		for (int k = 0; k < size2; k++) {
			al.get(k);
		}
		System.out.println((System.nanoTime() - startTime) + " alist 4realz");

		System.out.println("Get(50) measurement:");

		startTime = System.nanoTime();
		h.get(500);
		System.out.println((System.nanoTime() - startTime) + " hashmap");

		startTime = System.nanoTime();
		int size3 = al.size();
		for (int k = 0; k < size3; k++) {
			if (al.get(k).getIndex() == 500) {
				break;
			}
		}
		System.out.println((System.nanoTime() - startTime) + " alist 4realz");
	}
}
