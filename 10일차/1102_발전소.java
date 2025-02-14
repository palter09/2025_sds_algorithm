import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int N, P, INF = 3000000;
    static int[][] cost;
    static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 발전소 개수 입력
        N = Integer.parseInt(br.readLine().trim());
        cost = new int[N][N];

        // 발전소 간 전환 비용 입력
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                cost[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 현재 발전소 상태 입력
        String s = br.readLine().trim();
        int state = 0;
        for (int i = 0; i < N; i++) {
            if (s.charAt(i) == 'Y') {
                state |= (1 << i);  // 해당 발전소가 켜져 있음
            }
        }

        // 목표 발전소 개수 입력
        P = Integer.parseInt(br.readLine().trim());

        // 현재 켜진 발전소 개수 확인
        int initialCount = Integer.bitCount(state);
        if (initialCount >= P) {
            System.out.println(0);
            return;
        }

        // DP 테이블 초기화
        dp = new int[1 << N];
        Arrays.fill(dp, -1);

        // 최소 비용 계산
        int ans = tsp(state);
        System.out.println(ans == INF ? -1 : ans);
    }

    // 비트마스킹 DP
    static int tsp(int state) {
        int count = Integer.bitCount(state);
        if (count >= P) {
            return 0;
        }

        if (dp[state] != -1) {
            return dp[state];
        }

        int ret = INF;

        // 발전소 켜기
        for (int i = 0; i < N; i++) {
            if ((state & (1 << i)) == 0) {  // 아직 켜지지 않은 발전소
                for (int j = 0; j < N; j++) {
                    if ((state & (1 << j)) != 0) {  // 이미 켜진 발전소에서 연결 가능
                        int newState = state | (1 << i);
                        ret = Math.min(ret, tsp(newState) + cost[j][i]);
                    }
                }
            }
        }

        return dp[state] = ret;
    }
}