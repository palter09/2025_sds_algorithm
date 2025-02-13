import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N, tree[], MAX=1000001;

    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(br.readLine());
        tree = new int[MAX*4];
        int a, b, c;
        for (int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            if (a == 1) {
                b = Integer.parseInt(st.nextToken());
                int ans = search(1, 0, MAX-1, b);
                update(1, 0, MAX-1, ans, -1);
                sb.append(ans+"\n");
            } else {
                b = Integer.parseInt(st.nextToken());
                c = Integer.parseInt(st.nextToken());
                update(1, 0, MAX-1, b, c);
            }
        }
        System.out.println(sb.toString());
    }
    static int search(int node, int start, int end, int cnt) {
        if (start == end) {
            return start;
        }
        if (tree[node*2] < cnt) {
            return search(node*2+1, (start+end)/2+1, end, cnt-tree[node*2]);
        }
        return search(node*2, start, (start+end)/2, cnt);
    }
    static void update(int node, int start, int end, int idx, int diff) {
        if (idx<start || end<idx) {
            return;
        }
        tree[node] += diff;
        if (start!=end) {
            update(node*2, start, (start+end)/2, idx, diff);
            update(node*2+1, (start+end)/2+1, end, idx, diff);
        }
    }
}
