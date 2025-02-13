import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int arr[] = new int[N+1];
        int cost[] = new int[N+1];
        st = new StringTokenizer(br.readLine());
        for (int i=1; i<=N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        for (int i=1; i<=N; i++) {
            cost[i] = Integer.parseInt(st.nextToken());
        }
        // d[j] = j비용을 사용했을때 확보할 수 있는 최대 메모리양
        // 1. 앱을 그대로 둔다. d[j] (기존 확보한 양 그대로)
        // 2. 현재 앱을 끈다. d[j-cost[i]] + arr[i] 
        // => 현재 앱을 껐을때 j 비용이 되려면 d[j-cost[i]] 의 최대 메모리양에 
        // 현재 앱을 껐을때 확보되는 최대메모리를 더한다.
        int[] d = new int[10001];
        for (int i=1; i<=N; i++) {
            for (int j=10000; j>=0; j--) {
                if (j>=cost[i]) {
                    d[j] = Math.max(d[j], d[j-cost[i]]+arr[i]);
                }
            }
        }
        // 최소 비용을 구해야 하므로 낮은 비용에서 M 이상의 메모리가 확보된다면 답이다.
        int ans = 0;
        while (true) {
            if (d[ans] >= M) {
                break;
            }
            ans++;
        }
        System.out.println(ans);
    }
}
