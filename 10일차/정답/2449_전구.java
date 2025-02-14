import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N, K, arr[], d[][], INF=Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        arr = new int[N+1];
        d = new int[N+1][N+1];
        st = new StringTokenizer(br.readLine());
        for (int i=1; i<=N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        for (int gap=1; gap<=N; gap++) {
            for (int start=1; start+gap<=N; start++) {
                int tgt = start+gap;
                d[start][tgt] = INF;
                for (int k=start; k<tgt; k++) {
                    int ans = d[start][k] + d[k+1][tgt] + (arr[start] == arr[k+1] ? 0 : 1);
                    d[start][tgt] = Math.min(d[start][tgt], ans);
                }
            }
        }
        System.out.println(d[1][N]);

    }
}
