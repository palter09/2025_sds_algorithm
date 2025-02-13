import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int arr[], d[], path[], ans[], len, N, MAX=1000000;

    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        arr = new int[N+1];
        d = new int[MAX+1];
        path = new int[N+1];
        ans = new int[N+1];
        st = new StringTokenizer(br.readLine());
        for (int i=1; i<=N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        for (int i=0; i<=MAX; i++) {
            d[i] = Integer.MAX_VALUE;
        }

        int idx;
        for (int i=1; i<=N; i++) {
            idx = lowerBound(arr[i]);
            d[idx] = arr[i];
            path[i] = idx;
            len = Math.max(len, idx);
        }
        sb.append(len+"\n");
        int x = Integer.MAX_VALUE;
        idx = len;
        for (int i=N; i>=1; i--) {
            if(path[i]==idx && arr[i]<x) {
                ans[idx--] = arr[i];
                x = arr[i];
            }
        }
        for (int i=1; i<=len; i++) {
            sb.append(ans[i] + " ");
        }
        System.out.println(sb.toString());
    }
    static int lowerBound(int val) {
        int start = 1;
        int end = MAX;
        int mid;
        while (start < end) {
            mid = (start + end) / 2;
            if (d[mid] >= val) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }
}
