import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    static int T, M;
    static PriorityQueue<Integer> high, low;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        T = Integer.parseInt(st.nextToken());
        for (int t=0; t<T; t++) {
            M = Integer.parseInt(br.readLine());
            high = new PriorityQueue<Integer>((o1, o2) -> {
                return Integer.compare(o1, o2);
            });
            low = new PriorityQueue<Integer>((o1, o2) -> {
                return Integer.compare(o2, o1);
            });

            int n, tmp, mid=0;
            int cnt = 0, output = 0;
            sb.append((M+1)/2+"\n");
            for (int i=0; i<=(M-1)/10; i++) {
                st = new StringTokenizer(br.readLine());
                if (cnt==0) {
                    mid = Integer.parseInt(st.nextToken());
                    cnt++;
                    output++;
                    sb.append(mid+" ");
                }
                while (st.hasMoreTokens()) {
                    cnt++;
                    n = Integer.parseInt(st.nextToken());
                    if (mid > n) {
                        low.add(n);
                    } else {
                        high.add(n);
                    }
                    if (cnt%2==1) {
                        if (low.size()!=high.size()) {
                            if (low.size()>high.size()) {
                                tmp = low.poll();
                                high.add(mid);
                                mid = tmp;
                            } else {
                                tmp = high.poll();
                                low.add(mid);
                                mid = tmp;
                            }
                        }
                        sb.append(mid+" ");
                        output++;
                        if (output%10==0) {
                            sb.append("\n");
                        }
                    }
                }
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }
}
