package DataStructures;

class SegmentTree {   // Ova kje bide minimum Segment Tree, lesno mozhe da se napravi i max so promena vo BuildSegmentTree funkcijata
	int[] array;
	int[] st;
	
	public SegmentTree(int[] a) {
		array = a;
		st = new int[4*a.length];
		buildSegmentTree(1, 0, a.length-1);
	}

	void buildSegmentTree(int index, int L, int R) {
		if(L == R){
			st[index] = L;
		}
		else{
			buildSegmentTree(index*2, L, (L+R)/2);
			buildSegmentTree(index*2 + 1, (L+R)/2 +1, R);
			int index1 = st[index*2];
			int index2 = st[index*2 + 1];
			st[index] = (array[index1] > array[index2]) ? index2 : index1;   // ova odreduva deka e Min Segment Tree
		}
	}
	
	int RangeMinimumQuery(int L, int R){
		return findMin(1, 0, array.length - 1, L, R);
	}

	int findMin(int index, int L, int R, int l, int r) {
		if(l > R || r < L) return -1;
		if(L >= l && R <= r) return st[index];
		
		int index1 = findMin(index*2, L, (L+R)/2, l, r);
		int index2 = findMin(index*2 + 1, (L+R)/2 + 1, R, l, r);
		
		if(index1 == -1) return index2;
		if(index2 == -1) return index1;
		return (array[index1] <= array[index2]) ? index1 : index2;
	}
	
	int updateValue(int index, int newValue){
		return update(1, 0, array.length-1, index, newValue);
	}

	int update(int index, int L, int R, int idx, int newValue) {
		int i = idx, j = idx;
		
		if(j < L || i > R) return st[index];
		
		if(L==i && R == j){
			array[idx] = newValue;
			return st[index] = L;
		}
		
		int index1 = update(index*2, L, (L+R)/2, idx, newValue);
		int index2 = update(index*2 + 1, (L+R)/2 + 1, R, idx, newValue);
		
		return st[index] = (array[index1] <= array[index2]) ? index1 : index2;
	}
	
	
	//TEST
//	public static void main(String[] args) {
//		 int[] A = new int[] { 18, 17, 13, 19, 15, 11, 20 }; // the original array
//		    SegmentTree st = new SegmentTree(A);
//
//		    System.out.printf("              idx    0, 1, 2, 3, 4,  5, 6\n");
//		    System.out.printf("              A is {18,17,13,19,15, 11,20}\n");
//		    System.out.printf("RMQ(1, 3) = %d\n", st.RangeMinimumQuery(1, 3)); // answer = index 2
//		    System.out.printf("RMQ(4, 6) = %d\n", st.RangeMinimumQuery(4, 6)); // answer = index 5
//		    System.out.printf("RMQ(3, 4) = %d\n", st.RangeMinimumQuery(3, 4)); // answer = index 4
//		    System.out.printf("RMQ(0, 0) = %d\n", st.RangeMinimumQuery(0, 0)); // answer = index 0
//		    System.out.printf("RMQ(0, 1) = %d\n", st.RangeMinimumQuery(0, 1)); // answer = index 1
//		    System.out.printf("RMQ(0, 6) = %d\n", st.RangeMinimumQuery(0, 6)); // answer = index 5
//
//		    System.out.printf("              idx    0, 1, 2, 3, 4,  5, 6\n");
//		    System.out.printf("Now, modify A into {18,17,13,19,15,100,20}\n");
//		    st.updateValue(5, 100);                  // update A[5] from 11 to 100
//		    System.out.printf("These values do not change\n");
//		    System.out.printf("RMQ(1, 3) = %d\n", st.RangeMinimumQuery(1, 3));               // 2
//		    System.out.printf("RMQ(3, 4) = %d\n", st.RangeMinimumQuery(3, 4));               // 4
//		    System.out.printf("RMQ(0, 0) = %d\n", st.RangeMinimumQuery(0, 0));               // 0
//		    System.out.printf("RMQ(0, 1) = %d\n", st.RangeMinimumQuery(0, 1));               // 1
//		    System.out.printf("These values change\n");
//		    System.out.printf("RMQ(0, 6) = %d\n", st.RangeMinimumQuery(0, 6));            // 5->2
//		    System.out.printf("RMQ(4, 6) = %d\n", st.RangeMinimumQuery(4, 6));            // 5->4
//		    System.out.printf("RMQ(4, 5) = %d\n", st.RangeMinimumQuery(4, 5));            // 5->4
//	}
	
}
