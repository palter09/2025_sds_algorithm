import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N, K, a[][], d[];
    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        int w, v;
        a = new int[N+1][2];
        d = new int[10000001];
        for (int i=1; i<=10000000; i++) {
            d[i] = 987654321;
        }
        for (int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            a[i+1][0] = Integer.parseInt(st.nextToken());
            a[i+1][1] = Integer.parseInt(st.nextToken());
        }
        d[0] = 0;
        for (int i=1; i<=N; i++) {
            for (int k=10000000; k>=0; k--) {
                if (k >= a[i][1]) {
                    d[k] = Math.min(d[k], d[k-a[i][1]]+a[i][0]);
                }
            }
        }
        int p = 10000000;
        int ans = 0;
        while (p >= 0) {
            if (d[p]!=987654321 && d[p] <= K) {
                ans = p;
                break;
            }
            p--;
        }
        System.out.println(ans);
    }
}
