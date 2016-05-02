package lab_Stringovi;

public class LongestCommonSubsequence {
	
	static char[] LCS(String first, String second){
		
		int firstSize = first.length()+1;
		int secondSize = second.length()+1;
		int max = 0;
		
		int mat[][] = new int[secondSize+1][firstSize+1];
		
		for(int i=1;i<secondSize;i++){
			
			for(int j=1;j<firstSize;j++){
				if(second.charAt(i-1) == first.charAt(j-1)){
					mat[i][j] = mat[i-1][j-1] + 1;
				}
				else{
					mat[i][j] = Math.max(mat[i-1][j], mat[i][j-1]);
				}
				
				max = Math.max(max, mat[i][j]);
			}
			
		}
		
		//return max; ako se bara dolzina
		
		int i = secondSize - 1;
		int j = firstSize - 1;
		char[] lcs = new char[max];
		int curr = max-1;
		
		while(i!=0 &&  j!=0){
			if(mat[i][j]>mat[i-1][j] && mat[i][j]>mat[i][j-1]){
				lcs[curr] = first.charAt(j-1);
				curr --;
				i--;
				j--;
			}
			else if(mat[i][j]==mat[i-1][j]){
				i--;
			}
			else{ // mat[i][j] = mat[i][j-1]
				j--;
			}
		}
		
		return lcs;
	}
	
	
	public static void main(String[] args) {
		System.out.println(LCS("abcdaf", "acbcf"));
	}
}
