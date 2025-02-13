import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int arr[], N, d[][][];

    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        arr = new int[100001];
        st = new StringTokenizer(br.readLine());
        int t, n=0;
        while (true) {
            t = Integer.parseInt(st.nextToken());
            if (t == 0) {
                break;
            }
            arr[++n] = t;
        }
        d = new int[2][5][5];
        // 0 현재 상태, 1 이전 상태
        for (int i=0; i<5; i++) {
            for (int j=0; j<5; j++) {
                d[0][i][j] = Integer.MAX_VALUE;
                d[1][i][j] = Integer.MAX_VALUE;
            }
        }
        d[1][0][0] = 0;
        int pos;
        for (int k=1; k<=n; k++) {
            pos = arr[k];
            for (int i=0; i<5; i++) {
                for (int j=0; j<5; j++) {
                    if (d[1][i][j] == Integer.MAX_VALUE) {
                        continue;
                    }
                    d[0][pos][j] = Math.min(d[0][pos][j], d[1][i][j]+cost(i, pos));
                    d[0][i][pos] = Math.min(d[0][i][pos], d[1][i][j]+cost(j, pos));
                }
            }

            for (int i=0; i<5; i++) {
                for (int j=0; j<5; j++) {
                    d[1][i][j] = d[0][i][j];
                    d[0][i][j] = Integer.MAX_VALUE;
                }
            }

        }
        int ans = Integer.MAX_VALUE;
        for (int i=0; i<5; i++) {
            ans = Math.min(ans, d[1][arr[n]][i]);
            ans = Math.min(ans, d[1][i][arr[n]]);
        }
        System.out.println(ans);

    }
    static int cost(int from, int to) {
        if (from == 0) {
            return 2;
        }
        if (from == to) {
            return 1;
        }
        if ((from+to)%2==0) {
            return 4;
        }
        return 3;
    }
}
