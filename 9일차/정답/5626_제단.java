import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N, arr[], d[][], H=5000, MOD=1000000007;

    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(br.readLine());
        arr = new int[N+1];
        d = new int[2][H+3];
        st = new StringTokenizer(br.readLine());
        for (int i=1; i<=N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        if (arr[1] > 0 || arr[N] > 0) {
            System.out.println(0);
            return;
        }
        // 1 : 이전 단계, 0 : 현재 단계
        d[1][0]=1;
        int h;
        for (int i=2; i<=N; i++) {
            h = arr[i];
            if (h == -1) {
                for (int j=0; j<=H; j++) {
                    d[0][j] = d[1][j];
                    d[0][j] += d[1][j+1];
                    d[0][j] %= MOD;
                    if (j > 0) {
                        d[0][j] += d[1][j-1];
                        d[0][j] %= MOD;
                    }
                }
            } else {
                d[0][h] = d[1][h];
                d[0][h] += d[1][h+1];
                d[0][h] %= MOD;
                if (h > 0) {
                    d[0][h] += d[1][h-1];
                    d[0][h] %= MOD;
                }
            }
            for (int j=0; j<=H; j++) {
                d[1][j] = d[0][j];
                d[0][j] = 0;
            }
        }
        System.out.println(d[1][0]);
    }
}
