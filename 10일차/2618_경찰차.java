import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int N, W, INF = 2000000;
    static int d[];
    static ArrayList<int []> P;
    static ArrayList<int []> I;

    public static void main(String[] args) throws IOException  {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuffer sb = new StringBuffer();
        
        N = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        W = Integer.parseInt(st.nextToken());
        
        I = new ArrayList<>();
        P = new ArrayList<>();
        P.add(new int[] {1, 1});
        P.add(new int[] {N, N});
        d = new int[W];
        
        for (int i=0; i<W; i++) {
        	int x, y;
        	st = new StringTokenizer(br.readLine());
        	x = Integer.parseInt(st.nextToken());
        	y = Integer.parseInt(st.nextToken());
        	I.add(new int[] {x, y});
        }
        
        int ans = 0;
        for (int i=0; i<W; i++) {
        	int diff1 = Math.abs(I.get(i)[0] - P.get(0)[0]) + Math.abs(I.get(i)[1] - P.get(0)[1]);
            int diff2 = Math.abs(I.get(i)[0] - P.get(1)[0]) + Math.abs(I.get(i)[1] - P.get(1)[1]);
            
            if (diff1 < diff2) {
            	P.get(0)[0] = I.get(i)[0];
            	P.get(0)[1] = I.get(i)[1];
            	d[i] = 1;
            	ans += diff1;
            }
            
            else {
            	P.get(1)[0] = I.get(i)[0];
            	P.get(1)[1] = I.get(i)[1];
            	d[i] = 2;
            	ans += diff2;
            }
        }
        
        System.out.println(ans);
        
        for (int i=0; i<W; i++) {
        	System.out.println(d[i]);
        }
    }
    
}
