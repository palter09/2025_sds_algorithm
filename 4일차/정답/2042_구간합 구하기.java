import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N, M, K;
    static long[] arr;
    static long[] tree;

    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        arr = new long[N];
        tree = new long[N*4];

        for (int i = 0; i < N; i++) {
            arr[i] = Long.parseLong(br.readLine());
        }

        init(1, 0, N-1);

        for (int i = 0; i < M+K; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            if (a == 1) {
                int b = Integer.parseInt(st.nextToken());
                long c = Long.parseLong(st.nextToken());
                long diff = c - arr[b-1];
                arr[b-1] = c;
                update(1, 0, N-1, b-1, diff);
            } else {
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                sb.append(sum(1, 0, N-1, b-1, c-1) + "\n");
            }
        }
        System.out.println(sb.toString());

    }
    static long init(int node, int start, int end) {
        if (start == end) {
            return tree[node] = arr[start];
        }
        return tree[node] = init(node*2, start, (start+end)/2) + init(node*2+1, (start+end)/2+1, end);
    }
    static void update(int node, int start, int end, int idx, long diff) {
        if (idx < start || end < idx) {
            return;
        }
        tree[node] += diff;
        if (start != end) {
            update(node*2, start, (start+end)/2, idx, diff);
            update(node*2+1, (start+end)/2+1, end, idx, diff);
        }
        return;
    }
    static long sum(int node, int start, int end, int left, int right) {
        if (right < start || end < left) {
            return 0L;
        }
        if (left <= start && end <= right) {
            return tree[node];
        }
        return sum(node*2, start, (start+end)/2, left, right) + sum(node*2+1, (start+end)/2+1, end, left, right);
    }
}
