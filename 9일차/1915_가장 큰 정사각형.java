import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n, m;
    static int[][] A;
    static int[][] dp;

    public static void main(String[] args) throws IOException  {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        
        // 입력 받기
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        
        A = new int[n+1][m+1];
        dp = new int[n+1][m+1];

        for (int i = 0; i < n; i++) {
        	String s;
        	s = br.readLine();
        	String[] num = s.split("");
        	for (int j=0; j < m; j++) {
        		A[i][j] = Integer.parseInt(num[j]);
        		
        		if (i == 0 || j == 0) {
        			dp[i][j] = A[i][j];
        		}
        	}
        }
        
        int max_len = 0;
        for (int i=1; i<n; i++) {
        	for (int j=1; j<m; j++) {
        		int left = A[i][j-1];
        		int up = A[i-1][j];
        		int dia = A[i-1][j-1];
        		
        		if (left == up && left == dia) {
        			dp[i][j] = left + A[i][j];
        		}
        		
        		else {
        			int max = Math.max(left, up);
        			max = Math.max(max,  dia);
        			dp[i][j] = max;
        		}
        		
        		max_len = Math.max(max_len, dp[i][j]);
        	}
        }
        
        int ans = max_len * max_len;
        System.out.println(ans);
    }
}
