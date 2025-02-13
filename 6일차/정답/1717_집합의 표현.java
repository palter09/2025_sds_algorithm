import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N,M, p[];

    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        p = new int[N+1];
        for (int i=1; i<=N; i++) {
            p[i] = i;
        }
        int q, a, b;
        for (int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            q = Integer.parseInt(st.nextToken());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            if (q == 0) {
                union(a, b);
                continue;
            }
            if (find(a) == find(b)) {
                sb.append("YES\n");
            } else {
                sb.append("NO\n");
            }
        }
        System.out.println(sb.toString());
    }
    static void union(int a, int b) {
        int x = find(a);
        int y = find(b);
        if (x != y) {
            p[y] = x;
        }
    }
    static int find(int a) {
        if (a == p[a]) {
            return a;
        }
        p[a] = find(p[a]);
        return p[a];
    }
}
