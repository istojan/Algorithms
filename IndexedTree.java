package lab_drva;

public class IndexedTree {
	int N;
	int tree[];
	
	
	public IndexedTree(int[] arr){
		this.N = arr.length;
		this.tree = new int[N+1];
		for(int i=0;i<N;i++){
			update(N, i, arr[i]);
		}
	}
	
	void update(int n, int index, int val){
		index = index + 1;
		
		while(index<=n){
			tree[index] +=val;
			index+= index & (-index);
		}
	}
	
	int getSum(int index){
		int sum = 0;
		
		index = index + 1;
		
		while(index>0){
			sum+=tree[index];
			
			index -= index & (-index);
		}
		
		return sum;
	}
	
}
