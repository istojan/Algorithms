package lab_Stringovi;

public class KMP{
	
	/** 
	 * Presmetuva pomosna niza vo koja cuva golemina na
	 * sufiksot koja shto e ednakva so prefiksot
	 */
	static int[] prefixArray(String str){
		int size = str.length();
		int prefix[] = new int[size];
		
		int i = 1;
		int j = 0;
		
		while(i<size){
			if(str.charAt(i) == str.charAt(j)){
				prefix[i] = j + 1;
				i++;
				j++;
			}
			else if(j==0){
				prefix[i] = 0;
				i++;
			}
			else{
				j = prefix[j-1];
			}
		}
		
		return prefix;
	}
	
	/**
	 * Knuth-Morris-Pratt algoritam za naogjanje na patern
	 */
	static int patternSearch(String str, String pattern){
		int prefix[] = prefixArray(pattern);
		
		int ctr = 0;
		int size = str.length();
		int patternSize = pattern.length();
		int i = 0;
		int j = 0;
		
		while(i<size){
			if(str.charAt(i)==pattern.charAt(j)){
				i++;
				j++;
			}
			else if(j==0){
				i++;
			}
			else{
				j = prefix[j-1];
			}
			
			if(j==patternSize){
				ctr++;
				j = 0;
				// j = prefix[j-1];
				// return true ako se bara dali postoi
			}
		}
		
		return ctr;
		// return false; ako se bara dali postoi
	}
		
	public static void main(String[] args) {
		String str = "abxabcabcaby";
		String pattern = "abcaby";
		System.out.println(patternSearch(str, pattern));
		
	}
}
