package lab_drva;

public class SegmentTree {
	int tree[];
	
	public SegmentTree(int arr[]){
		
		int N = arr.length;
		
		int x = (int) (Math.ceil(Math.log(N)/Math.log(2)));
		
		int max_size = 2 * (int) Math.pow(2, x)-1;
		
		tree = new int[max_size];
		
		constructSegmentTree(arr, 0, N-1, 0);
		
	}
	
	int constructSegmentTree(int arr[], int begin, int end, int treeNode){
		if(begin==end){
			tree[treeNode] = arr[begin];
			return arr[begin];
		}
		
		int mid = (begin + end)/2;
		tree[treeNode] = constructSegmentTree(arr, begin, mid, treeNode*2 +1) +
				constructSegmentTree(arr, mid+1, end, treeNode*2 +2);
		return tree[treeNode];
	}
	
	void update(int begin, int end, int index, int diff, int treeNode){
		
		if(index <begin || index > end) return;
		
		tree[treeNode] = tree[treeNode] + diff;
		
		if(begin!=end){
			int mid = (begin+end)/2;
			update(begin, mid, index, diff, 2*treeNode + 1);
			update(mid+1, end, index, diff, 2*treeNode + 2);
		}
	}
	
	int getSum(int begin, int end, int queryBegin, int queryEnd, int treeNode){
		
		if(queryBegin<=begin && queryEnd>=end) return tree[treeNode];
		
		if(end<queryBegin || begin>queryEnd) return 0;
		
		int mid = (begin+end)/2;
		
		return getSum(begin, mid, queryBegin, queryEnd, 2*treeNode + 1) +
				getSum(mid+1, end, queryBegin, queryEnd, 2*treeNode + 2);
	}
	
	int getMin(int begin, int end, int queryBegin, int queryEnd, int treeNode){
		
		if(queryBegin<=begin && queryEnd>=end) return tree[treeNode];
		
		if(end<queryBegin || begin>queryEnd) return 0;
		
		int mid = (begin+end)/2;
		
		return Math.min(getMin(begin, mid, queryBegin, queryEnd, 2*treeNode + 1),
				getMin(mid+1, end, queryBegin, queryEnd, 2*treeNode + 2));
	
	}
	
}

class SegmentTreeParenthesis{
	
	Node[] tree;
	
	static class Node {
		   
	    int numOpened;
	    int numClosed;
	    int numCorrectSub;
	   
	   
	    public Node(int numOpened, int numClosed, int numCorrectSub) {
	        this.numOpened = numOpened;
	        this.numClosed = numClosed;
	        this.numCorrectSub = numCorrectSub;
	    }
	   
	    public Node() {
	       
	    }
	}
	
	SegmentTreeParenthesis(String str)
    {
        int N = str.length();
       
       
        // visinata na drvoto
        int x = (int) (Math.ceil(Math.log(N) / Math.log(2)));
 
        // kolku elementi se potrebni vo drvoto ~ 2*N - 1
        int max_size = 2 * (int) Math.pow(2, x) - 1;
 
       
       
        tree = new Node[max_size]; // Memory allocation
 
        // ja povikuvame pomoshnata funkcija za kreiranje na segmentnoto drvo
        constructSegmentTree(str, 0, N - 1, 0);
    }
	
	int getMid(int s, int e) {
        return s + (e - s) / 2;
    }

	Node constructSegmentTree(String str, int begin, int end, int treeNode) {
		if (begin == end) {
            if(str.charAt(begin) == '(') {
                tree[treeNode] = new Node(1, 0, 0);
            } else {
                tree[treeNode] = new Node(0, 1, 0);
            }
            return tree[treeNode];
        }
		
		int mid = (begin+end)/2;
		
		Node leftChild = constructSegmentTree(str, begin, mid, 2*treeNode+ 1);
        Node rightChild =  constructSegmentTree(str, mid + 1, end, 2*treeNode + 2);
       
        int temp = Math.min(leftChild.numOpened, rightChild.numClosed);
       
        Node newNode = new Node();
       
        newNode.numCorrectSub = leftChild.numCorrectSub + rightChild.numCorrectSub + temp;
        newNode.numOpened = leftChild.numOpened + rightChild.numOpened - temp;
        newNode.numClosed = leftChild.numClosed + rightChild.numClosed - temp;
       
        tree[treeNode] = newNode;
        return tree[treeNode];
		
	}
	
	public Node getSol(int begin, int end, int queryBegin, int queryEnd, int treeNode) {
	       
        if(begin > queryEnd) return new Node(0, 0, 0);
        if(end < queryBegin) return new Node(0,0,0);
       
        if(begin >= queryBegin && end <= queryEnd) return tree[treeNode];
       
       
        int mid = (begin + end) / 2;
        Node leftChild = getSol(begin, mid, queryBegin, queryEnd, 2 * treeNode + 1);
        Node rightChild = getSol(1 + mid, end, queryBegin, queryEnd, 2 * treeNode + 2);
       
 
       
        int temp = Math.min(leftChild.numOpened, rightChild.numClosed);
       
        int corr = leftChild.numCorrectSub + rightChild.numCorrectSub + temp;
        int o = leftChild.numOpened + rightChild.numOpened - temp;
        int c = leftChild.numClosed + rightChild.numClosed - temp;
       
        return new Node(o,c,corr);
    }
	
}


