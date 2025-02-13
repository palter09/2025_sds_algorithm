import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N, arr[], d[];
    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(br.readLine());
        arr = new int[N+1];
        d = new int[N+1];
        st = new StringTokenizer(br.readLine());
        for (int i=1; i<=N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        d[1] = 1;
        for (int i=2; i<=N; i++) {
            d[i] = 1;
            for (int k=1; k<i; k++) {
                if (arr[k] < arr[i]) {
                    d[i] = Math.max(d[i], d[k]+1);
                }
            }
        }
        int ans = 0;
        for (int i=1; i<=N; i++) {
            ans = Math.max(ans, d[i]);
        }
        System.out.println(ans);
    }
}
