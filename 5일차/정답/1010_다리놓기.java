import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int T, N, M;
    static int[][] d;

    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        T = Integer.parseInt(br.readLine());
        d = new int[31][31];
        d[0][0] = 1;
        for (int i=1; i<=30; i++) {
            d[i][0]=1;
            for(int j=1; j<=i; j++) {
                d[i][j] = d[i-1][j-1]+d[i-1][j];
            }
        }
        while (T>0) {
            T--;
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            sb.append(d[M][N]+"\n");
        }
        System.out.println(sb.toString());

    }
}
