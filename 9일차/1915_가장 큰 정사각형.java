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

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        A = new int[n][m];
        dp = new int[n][m];

        int max_len = 0;
        for (int i = 0; i < n; i++) {
            String s = br.readLine();
            for (int j = 0; j < m; j++) {
                // 문자 하나씩 읽어서 정수로 변환 (문자 '0' 또는 '1')
                A[i][j] = s.charAt(j) - '0';
                dp[i][j] = A[i][j];  // 첫 행, 첫 열은 그대로 복사
                max_len = Math.max(max_len, dp[i][j]);
            }
        }

        // dp 갱신: 각 칸을 오른쪽 아래 꼭짓점으로 하는 정사각형의 최대 한 변 길이
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (A[i][j] == 1) {
                    dp[i][j] = Math.min(Math.min(dp[i-1][j], dp[i][j-1]), dp[i-1][j-1]) + 1;
                    max_len = Math.max(max_len, dp[i][j]);
                } else {
                    dp[i][j] = 0;
                }
            }
        }

        System.out.println(max_len * max_len);
    }
}