import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    static int N, K, C;
    static ArrayList<int[]> jew;
    static ArrayList<Integer> bag;
    static PriorityQueue<Integer> pq;

    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        jew = new ArrayList<int[]>();
        bag = new ArrayList<Integer>();
        for (int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            jew.add(new int[] {Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())});
        }
        for (int i=0; i<K; i++) {
            bag.add(Integer.parseInt(br.readLine()));
        }
        Collections.sort(jew, (o1, o2) -> {
            return o1[0] - o2[0];
        });
        Collections.sort(bag, (o1, o2) -> {
            return o1 - o2;
        });
        pq = new PriorityQueue<Integer>((o1, o2) -> {
            return o2 - o1;
        });
        int cur, jp = 0;
        long ans = 0L;
        for (int i=0; i<K; i++) {
            cur = bag.get(i);
            while (jp < N && jew.get(jp)[0] <= cur) {
                pq.add(jew.get(jp)[1]);
                jp += 1;
            }
            if (!pq.isEmpty()) {
                ans += pq.poll();
            }
        }
        System.out.println(ans);
    }
}
