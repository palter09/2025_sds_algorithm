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
           
           int num;
           st = new StringTokenizer(br.readLine());
           for (int i=0; i<N; i++) {
        	   num = Integer.parseInt(st.nextToken());
        	   A[i] = num;
        	   longS[i] = 1;
           }
           
           for (int i=1; i<N; i++) {
        	   int max = 0;
        	   for (int j=0; j<i; j++) {
        		   if (A[j] < A[i]) {
        			   max = Math.max(max, longS[j]);
            		   longS[i] = max + 1;
        		   }
        	   }
        	   
           }
           
           int ans = 0;
           for (int i=0; i<N; i++) {
        	   ans = Math.max(longS[i], ans);
           }
           System.out.println(ans);
    }
}



