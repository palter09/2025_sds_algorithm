import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N, a[][], d[][];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        N = Integer.parseInt(br.readLine());
        a = new int[N+1][N+1];
        for (int i=1; i<=N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=1; j<=i; j++) {
                a[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        // i,j 위치에서의 최대값
        d = new int[N+1][N+1];
        for (int i=1; i<=N; i++) {
            for (int j=1; j<=i; j++) {
                int left = d[i-1][j-1];
                int right = d[i-1][j];
                d[i][j] = Math.max(left, right) + a[i][j];
            }
        }
        int ans = 0;
        for (int i=1; i<=N; i++) {
            ans = Math.max(ans, d[N][i]);
        }
        System.out.println(ans);
//      System.out.println(solve(1, 1));
    }
    static int solve(int i, int j) {
        if (i == N) {
            return a[i][j];
        }
        if (d[i][j] != 0) {
            return d[i][j];
        }

        int left = solve(i+1, j);
        int right = solve(i+1, j+1);
        d[i][j] = Math.max(left, right) + a[i][j];
        return d[i][j];
    }
}
