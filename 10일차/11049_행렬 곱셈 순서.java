import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int N, mat[][], d[][];
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        mat = new int [N+1][2];
        for (int i=1; i<=N; i++) {
        	st = new StringTokenizer(br.readLine());
        	mat[i][0] = Integer.parseInt(st.nextToken());
        	mat[i][1] = Integer.parseInt(st.nextToken());
        }
        d = new int[N+1][N+1];
        for (int i=1; i<N; i++) {
        	d[i][i+1] = mat[i][0] * mat[i][1] * mat[i+1][1];
        }
        
        // ABCED -> gap=2면 ABC
        for (int gap=2; gap<N; gap++) {
        	
        	for (int start=1; start+gap<=N; start++) {
        		int end = start + gap;
        		// gap=2일때 ABC BCD CDE
        		d[start][end] = Integer.MAX_VALUE;
        		// k: 분할지점
        		// ABCD에서 k가 start면 A/BCD AB/CD ABC/D
        		for (int k=start; k<end; k++) {
        			d[start][end] = Math.min(d[start][end], 
        						d[start][k] + d[k+1][end] + mat[start][0]*mat[k][1]*mat[end][1]);
        		}
        	}
        }
        
        System.out.println(d[1][N]);
    }
}
