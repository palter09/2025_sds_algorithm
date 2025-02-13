import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int R, C, map[][], l[][], r[][];

    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        map = new int[R+2][C+2];
        // l : 왼쪽부터 연속으로 내려오는 숫자, r: 오른쪽부터 연속으로
        l = new int[R+2][C+2];
        r = new int[R+2][C+2];
        int ans = 0, min;
        for (int i=1; i<=R; i++) {
            String s = br.readLine();

            for (int j=1; j<=C; j++) {
                map[i][j] = s.charAt(j-1) - '0';

                if (map[i][j] == 1) {
                    // 연속으로 내려오는 숫자 계산
                    l[i][j] = l[i-1][j-1]+1;
                    r[i][j] = r[i-1][j+1]+1;

                    // i,j 를 아래쪽 모서리로 하는 다이아몬드를 만들 수 있는 최대 값
                    min = Math.min(l[i][j], r[i][j]);
                    // i,j를 아래쪽 모서리로 하는 다이아몬드는 여러개가 있을 수 도 있어 모두 확인
                    while (ans < min) {
                        // 왼쪽 모서리까지 오른쪽으로 내려오는 대각선이 있는지 확인
                        // 오른쪽 모서리까지 왼쪽으로 내려오는 대각선이 있는지 확인
                        if (r[i-min+1][j-min+1] >= min 
                                && l[i-min+1][j+min-1] >= min) {
                            ans = Math.max(ans, min);
                        }
                        min--;
                    }
                }

            }
        }
        System.out.println(ans);
    }
}
