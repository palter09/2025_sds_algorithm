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
        d = new int[K+1];
        for (int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            a[i+1][0] = Integer.parseInt(st.nextToken());
            a[i+1][1] = Integer.parseInt(st.nextToken());
        }
        for (int i=1; i<=N; i++) {
            for (int k=0; k<=K; k++) {
                if (a[i][0]+k <= K) {
                    d[k] = Math.max(d[k], d[k+a[i][0]]+a[i][1]);
                }
            }
        }
        int ans=0;
        for (int i=0; i<=K; i++) {
            ans = Math.max(ans, d[i]);
        }
        System.out.println(ans);
    }
