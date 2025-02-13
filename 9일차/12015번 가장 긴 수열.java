import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static int N;
	static int [] A;
	static int [] longS;
	
    public static void main(String[] args) throws IOException  {
           BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
           StringTokenizer st = new StringTokenizer(br.readLine());
           StringBuffer sb = new StringBuffer();
           
           N = Integer.parseInt(st.nextToken());
           
           A = new int[N+1];
           longS = new int[N+1];
           
           st = new StringTokenizer(br.readLine());
           for (int i=0; i<N; i++) {
        	   A[i] = Integer.parseInt(st.nextToken());
        	   longS[i] = 1000000;
           }
           
           for (int i=0; i<N; i++) {
        	   int idx = lbound(A[i]);
        	   System.out.println("idx : " + idx);
        	   longS[idx] = A[i];
           }
           
           int ans = 0;
           for (int i=0; i<N; i++) {
        	   System.out.println(longS[i]);
        	   ans = Math.max(longS[i], ans);
           }
           System.out.println(ans);
    }
    
    static int lbound(int num) {
    	int left = 0;
    	int right = longS.length;
    	while (left < right) {
    		int mid = (left + right) / 2;
    		if (longS[mid] >= num) {
    			right = mid;
    		}
    		else {
    			left = mid+1;
    		}
    	}
    	return N/2 - left ;
    }
}



