import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[][] map = new int[N+1][M+1];
        for (int i=1; i<=N; i++) {
            String s = br.readLine();
            for (int j=1; j<=M; j++) {
                map[i][j] = s.charAt(j-1)-'0';
            }
        }
        int[][] d = new int[N+1][M+1];
        int ans = 0;
        for (int i=1; i<=N; i++) {
            for (int j=1; j<=M; j++) {
                if (map[i][j] == 1) {
                    int min = 1111;
                    min = Math.min(d[i-1][j], d[i-1][j-1]);
                    min = Math.min(min, d[i][j-1]);
                    d[i][j] = min+1;
                    ans = Math.max(ans, d[i][j]);
                }
            }
        }
        System.out.println(ans*ans);
    }
}
