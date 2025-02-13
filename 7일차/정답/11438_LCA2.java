import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static int N, M;
    static ArrayList<ArrayList<Integer>> e;
    static int[] depth;
    static int[][] parent;
    static Queue<Integer> q;

    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(br.readLine());
        e = new ArrayList<ArrayList<Integer>>(N+1);
        for (int i=0; i<=N; i++) {
            e.add(new ArrayList<Integer>());
        }
        int a, b;
        for (int i=0; i<N-1; i++) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            e.get(a).add(b);
            e.get(b).add(a);
        }
        depth = new int[N+1];
        parent = new int[30][N+1];
        q = new LinkedList<Integer>();
        q.add(1);
        depth[1] = 1;
        while (!q.isEmpty()) {
            int el = q.poll();
            for (int i=0; i<e.get(el).size(); i++) {
                int next = e.get(el).get(i);
                if (depth[next] == 0) {
                    depth[next] = depth[el]+1;
                    parent[0][next] = el;
                    q.add(next);
                }
            }
        }
        for (int i=1; i<=29; i++) {
            for (int j=1; j<=N; j++) {
                parent[i][j] = parent[i-1][parent[i-1][j]];
            }
        }
        M = Integer.parseInt(br.readLine());
        for (int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            sb.append(lca(a,b)+"\n");
        }
        System.out.println(sb.toString());
    }
    static int lca(int x, int y) {
        if (depth[x] > depth[y]) {
            int temp = x;
            x = y;
            y = temp;
        }
        for (int i=29; i>=0; i--) {
            if (depth[y]-depth[x] >= (1 << i)) {
                y = parent[i][y];
            }
        }
        if (x == y) {
            return x;
        }
        for (int i=29; i>=0; i--) {
            if (parent[i][x]!=parent[i][y]) {
                x = parent[i][x];
                y = parent[i][y];
            }
        }
        return parent[0][x];
    }
}
