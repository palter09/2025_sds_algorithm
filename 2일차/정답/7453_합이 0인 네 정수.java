import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int N, A[], B[], C[], D[];
    static long first[], second[];

    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        N = Integer.parseInt(st.nextToken());
        A = new int[N];
        B = new int[N];
        C = new int[N];
        D = new int[N];
        first = new long[N*N];
        second = new long[N*N];

        for (int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            A[i] = Integer.parseInt(st.nextToken());
            B[i] = Integer.parseInt(st.nextToken());
            C[i] = Integer.parseInt(st.nextToken());
            D[i] = Integer.parseInt(st.nextToken());
        }
        int t = 0;
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                first[t] = (long)A[i]+(long)B[j];
                second[t] = (long)C[i]+(long)D[j];
                t++;
            }
        }
        Arrays.sort(first);
        Arrays.sort(second);

        int left = 0;
        int right = N*N-1;
        long temp1, temp2, ans = 0, cnt1, cnt2;
        while (left<N*N && right >= 0) {
            temp1 = first[left];
            temp2 = second[right];
            if (temp1+temp2 < 0) {
                left++;
                continue;
            }
            if (temp1+temp2 > 0) {
                right--;
                continue;
            }
            cnt1 = 0;
            cnt2 = 0;
            while (left < N*N) {
                if (first[left] == temp1) {
                    cnt1++;
                    left++;
                } else {
                    break;
                }
            }
            while (right >= 0) {
                if (second[right] == temp2) {
                    cnt2++;
                    right--;
                } else {
                    break;
                }
            }
            ans += cnt1*cnt2;
        }
        System.out.println(ans);
    }
}
