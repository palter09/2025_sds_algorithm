import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
/**
 * 
13
0
1
1
1
2
2
4
4
4
5
5
8
8
 */
public class Main {

    static int N, parent[][], depth[];
    static ArrayList<ArrayList<Integer>> edges;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(br.readLine());
        parent = new int[10][N+1];
        depth = new int[N+1];
        edges = new ArrayList<>();
        for (int i=0; i<=N; i++) {
            edges.add(new ArrayList<>());
        }
        for (int i=1; i<=N; i++) {
            int n = Integer.parseInt(br.readLine());
            parent[0][i] = n;
            edges.get(n).add(i);
        }
        // 모든 노드의 Depth 계산
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(1);
        int cur, next;
        while (!q.isEmpty()) {
            cur = q.poll();
            for (int i=0; i<edges.get(cur).size(); i++) {
                next = edges.get(cur).get(i);
                depth[next] = depth[cur]+1;
                q.add(next);
            }
        }
        // N의 2의K번째 부모를 저장하는 DP 테이블 계산
        for (int k=1; k<=9; k++) {
            for (int i=1; i<=N; i++) {
                parent[k][i] = parent[k-1][parent[k-1][i]];
            }
        }
        System.out.println(lca(6, 3));
        System.out.println(lca(7, 13));

    }
    static int lca(int a, int b) {
        // 높이를 맞추기 위해 b가 더 큰 depth 가 되도록
        if (depth[a] > depth[b]) {
            int temp = a;
            a = b;
            b = temp;
        }
        // b를 a와 b의 높이 차이만큼 올려서 높이를 맞춘다.
        for (int k=9; k>=0; k--) {
            if (depth[b]-depth[a] >= (1 << k)) {
                b = parent[k][b];
            }
        }
        // 높이가 같아졌는데 같은곳이라면 a를 반환
        if (a==b) {
            return a;
        }
        // 공통조상이 다르다면 거슬러 올라간다.
        for (int k=9; k>=0; k--) {
            if (parent[k][a] != parent[k][b]) {
                a = parent[k][a];
                b = parent[k][b];
            }
        }
        return parent[0][a];
    }
}
