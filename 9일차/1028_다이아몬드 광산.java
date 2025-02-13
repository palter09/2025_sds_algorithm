import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int R, C;
    static int[][] A;
    static int[][] dp;

    public static void main(String[] args) throws IOException  {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        A = new int[R][C];
        dp = new int[R][C];

        int max_len = 0;
        for (int i = 0; i < R; i++) {
            String s = br.readLine();
            for (int j = 0; j < C; j++) {
                // 문자 하나씩 읽어서 정수로 변환 (문자 '0' 또는 '1')
                A[i][j] = s.charAt(j) - '0';
                dp[i][j] = A[i][j];  // 첫 행, 첫 열은 그대로 복사
                max_len = Math.max(max_len, dp[i][j]);
            }
        }

        // dp 갱신: 각 칸을 아래 꼭짓점으로 하는 다이아몬드의 최대 한 변 길이
        for (int i = 2; i < R; i++) {
            for (int j = 1; j < C-1; j++) {
                if (A[i][j] == 1) {
                    dp[i][j] = Math.min(Math.min(dp[i-2][j], dp[i-1][j-1]), dp[i-1][j+1]) + 1;
                    max_len = Math.max(max_len, dp[i][j]);
                } else {
                    dp[i][j] = 0;
                }
            }
        }
        
        for (int i=0; i<R; i++) {
        	for (int j=0; j<C; j++) {
        		System.out.print(dp[i][j]);
        	}
        	System.out.println("");
        }

        System.out.println(max_len);
    }
}
