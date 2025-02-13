import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {

    static int N, tree[];
    static ArrayList<int[]> arr;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(br.readLine());
        arr = new ArrayList<int[]>();
        for (int i=0; i<N; i++) {
            arr.add(new int[] {i, Integer.parseInt(br.readLine())});
        }
        Collections.sort(arr, (o1, o2) -> {
            return o2[1] - o1[1];
        });
        for (int i=0; i<N; i++) {
            arr.get(i)[1] = i;
        }
        Collections.sort(arr, (o1, o2) -> {
            return o1[0] - o2[0];
        });
        tree = new int[N*4];
        for (int i=0; i<N; i++) {
            int index = arr.get(i)[1];
            sb.append((query(1, 0, N-1, 0, index)+1)+"\n");
            update(1, 0, N-1, index, 1);
        }
        System.out.println(sb.toString());

    }
    static void update(int node, int start, int end, int idx, int diff) {
        if (idx < start || end < idx) {
            return;
        }
        tree[node] += 1;
        if (start!=end) {
            int mid = (start+end)/2;
            update(node*2, start, mid, idx, diff);
            update(node*2+1, mid+1, end, idx, diff);
        }
    }
    static int query(int node, int start, int end, int left, int right) {
        if (right < start || end < left) {
            return 0;
        }
        if (left<=start&&end<=right) {
            return tree[node];
        }
        int mid = (start+end)/2;
        return query(node*2, start, mid, left, right) + query(node*2+1, mid+1, end, left, right);

    }

}
