import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

    static int V, E, order;
    static ArrayList<ArrayList<Integer>> e;
    static int[] visited, ap;

    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        e = new ArrayList<ArrayList<Integer>>(V+1);
        visited = new int[V+1];
        ap = new int[V+1];
        for (int i=0; i<=V; i++) {
            visited[i] = -1;
            e.add(new ArrayList<Integer>());
        }
        int a, b;
        for (int i=0; i<E; i++) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            e.get(a).add(b);
            e.get(b).add(a);
        }
        order = 0;
        for (int i=1; i<=V; i++) {
            if (visited[i] == -1) {
                dfs(i, -1);
            }
        }
        int ans = 0;
        for (int i=1; i<=V; i++) {
            if (ap[i] == 1) {
                ans++;
                sb.append(i+" ");
            }
        }
        System.out.println(ans);
        System.out.println(sb.toString());

    }
    static int dfs(int node, int parent) {
        visited[node] = ++order;
        int ret = visited[node];
        int child = 0;
        int next, low;
        for (int i=0; i<e.get(node).size(); i++) {
            next = e.get(node).get(i);
            if (next == parent) {
                continue;
            }
            if (visited[next] == -1) {
                low = dfs(next, node);
                if (parent != -1 && visited[node] <= low) {
                    ap[node] = 1;
                }
                ret = Math.min(ret, low);
                child++;
            } else {
                ret = Math.min(ret, visited[next]);
            }
        }
        if (parent == -1 && child > 1) ap[node] = 1;
        return ret;
    }
}
