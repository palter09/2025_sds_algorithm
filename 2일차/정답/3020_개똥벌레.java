import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int N, H;
    static int[] a, b;
    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        a = new int[N/2];
        b = new int[N/2];
        for (int i=0; i<N/2; i++) {
            a[i] = Integer.parseInt(br.readLine());
            b[i] = Integer.parseInt(br.readLine());
        }
        Arrays.sort(a);
        Arrays.sort(b);
        int min = 200001;
        int cnt = 1;
        for (int i=1; i<=H; i++) {
            int conflict = lbound(i, a)+lbound(H-i+1, b);
            if (conflict == min) {
                cnt++;
                continue;
            }
            if (min > conflict) {
                min = conflict;
                cnt = 1;
            }
        }
        System.out.println(min + " " + cnt);
    }
    static int lbound(int h, int[] arr) {
        int left = 0;
        int right = arr.length;
        while (left < right) {
            int mid = (left+right)/2;
            if (arr[mid] >= h) {
                right = mid;
            } else {
                left = mid+1;
            }
        }
        return N/2-left;
    }
}
