package lab_Stringovi;

import java.util.Stack;

public class SmithWaterman_Custom {
	static int hit = 8;
	static int miss = -5;
	static int gap = -3;
	
	public static void main(String[] args) {
//		System.out.println(runSmithWaterman("asads", "aqads"));
		System.out.println(runSmithWaterman("CTTAACT", "CGGATCAT"));
	}

	private static int runSmithWaterman(String first, String second) {
		int[][] mat = new int[first.length()+1][second.length()+1];
		char[][] matDir = new char[first.length()+1][second.length()+1];
		int max = -1;
		
		// construct a second matrix with arrows
		for(int i=1;i<first.length()+1;i++){
			for(int j=1;j<second.length()+1;j++){
				if(first.charAt(i-1) == second.charAt(j-1)){
					if(mat[i-1][j]+gap > mat[i][j-1] + gap){
						mat[i][j] = mat[i-1][j] + gap;
						matDir[i][j] = 'U';
						
					}
					else{
						mat[i][j] = mat[i][j-1] + gap;
						matDir[i][j] = 'L';
						
						
					}
					if(mat[i][j]<mat[i-1][j-1]+hit){
						mat[i][j] = mat[i-1][j-1] + hit;
						matDir[i][j] = 'D';
					}
					if(mat[i][j]<0){
						mat[i][j] = 0;
						matDir[i][j] = 'S';
					}
//					mat[i][j] = Math.max( mat[i-1][j]+gap, mat[i][j-1]+gap);
//					mat[i][j] = Math.max( mat[i][j], mat[i-1][j-1] + hit);
//					mat[i][j] = Math.max( mat[i][j], 0); // ako e globalno go brisheme posledniov red
				}
				else{
					if(mat[i-1][j]+gap > mat[i][j-1] + gap){
						mat[i][j] = mat[i-1][j] + gap;
						matDir[i][j] = 'U';
						
					}
					else{
						mat[i][j] = mat[i][j-1] + gap;
						matDir[i][j] = 'L';
						
						
					}
					if(mat[i][j]<mat[i-1][j-1]+miss){
						mat[i][j] = mat[i-1][j-1] + miss;
						matDir[i][j] = 'D';
					}
					if(mat[i][j]<0){
						mat[i][j] = 0;
						matDir[i][j] = 'S';
					}
//					mat[i][j] = Math.max( mat[i-1][j]+gap, mat[i][j-1]+gap);
//					mat[i][j] = Math.max( mat[i][j], mat[i-1][j-1] + miss);
//					mat[i][j] = Math.max( mat[i][j], 0); // kaj globalno go brisheme posledniov red
				}
				//max = Math.max(max, mat[i][j]);
			}
		}
		max = -1;
		int indexi = -1;
		int indexj = -1;
		
		for(int i = 0;i < first.length() + 1; i++){
			for(int j = 0;j < second.length() + 1; j++){
				System.out.print(mat[i][j] + " ");
				if(mat[i][j]>max){
					max = mat[i][j];
					indexi = i;
					indexj = j;
				}
			}
			System.out.println();
		}
		
//		for(int i = 0;i < first.length() + 1; i++){
//			for(int j = 0;j < second.length() + 1; j++){
//				System.out.print(matDir[i][j] + " ");
//			}
//			System.out.println();
//		}
		
		
		int i = indexi;
		int j = indexj;
		Stack<Character> stackFirst = new Stack<>();
		Stack<Character> stackSecond = new Stack<>();
		
		while(i!=0 && j!=0 && matDir[i][j]!='S'){
			if(matDir[i][j]=='D'){
				stackFirst.add(first.charAt(i-1));
				stackSecond.add(second.charAt(j-1));
				i--;
				j--;
			}
			else if(matDir[i][j]=='U'){
				stackFirst.add(first.charAt(i-1));
				stackSecond.add('-');
				
				i--;
			}
			else if(matDir[i][j]=='L'){
				stackFirst.add('-');
				stackSecond.add(second.charAt(j-1));
				j--;
			}
		}
		String str1 = "";
		String str2 = "";
		while(!stackFirst.isEmpty()){
			str1 += stackFirst.pop();
			str2 += stackSecond.pop();
		}
		System.out.println(str1);
		System.out.println(str2);
		
		
		return max;
//		Stack<String> actions = new Stack<String>();
//
//		while (i != 0 && j != 0) {
//			// diag case
//			if (Math.max(mat[i - 1][j - 1],
//					Math.max(mat[i - 1][j], mat[i][j - 1])) == mat[i - 1][j - 1]) {
//				actions.push("align");
//				i = i - 1;
//				j = j - 1;
//				// left case
//			} else if (Math.max(mat[i - 1][j - 1],
//					Math.max(mat[i - 1][j], mat[i][j - 1])) == mat[i][j - 1]) {
//				actions.push("insert");
//				j = j - 1;
//				// up case
//			} else {
//				actions.push("delete");
//				i = i - 1;
//			}
//		}
//
//		String alignOne = new String();
//		String alignTwo = new String();
//
//		@SuppressWarnings("unchecked")
//		Stack<String> backActions = (Stack<String>) actions.clone();
//		for (int z = 0; z < first.length(); z++) {
//			alignOne = alignOne + first.charAt(z);
//			if (!actions.empty()) {
//				String curAction = actions.pop();
//				// System.out.println(curAction);
//				if (curAction.equals("insert")) {
//					alignOne = alignOne + "-";
//					while (actions.peek().equals("insert")) {
//						alignOne = alignOne + "-";
//						actions.pop();
//					}
//				}
//			}
//		}
//
//		for (int z = 0; z < second.length(); z++) {
//			alignTwo = alignTwo + second.charAt(z);
//			if (!backActions.empty()) {
//				String curAction = backActions.pop();
//				if (curAction.equals("delete")) {
//					alignTwo = alignTwo + "-";
//					while (backActions.peek().equals("delete")) {
//						alignTwo = alignTwo + "-";
//						backActions.pop();
//					}
//				}
//			}
//		}
//
//		// print alignment
//		System.out.println(alignOne + "\n" + alignTwo);

	}
}
