import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    // arr: 입력된 방향(번호)을 저장하는 배열
    // N: 입력된 숫자의 개수 (현재 사용되지는 않음)
    // d: 동적 계획법(DP) 배열. d[0]는 현재 상태, d[1]은 이전 상태를 저장.
    static int arr[], N, d[][][];

    public static void main(String[] args) throws IOException {
        // 입력을 위한 BufferedReader와 토크나이저 준비
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        // 최대 100,000개의 입력을 받을 수 있도록 배열 크기를 할당 (인덱스 1부터 사용)
        arr = new int[100001];
        st = new StringTokenizer(br.readLine());
        int t, n = 0;
        // 입력이 0이 나오면 종료, 그 전까지 숫자를 arr에 저장
        while (true) {
            t = Integer.parseInt(st.nextToken());
            if (t == 0) { // 입력이 0이면 반복 종료
                break;
            }
            arr[++n] = t; // 숫자를 1부터 저장
        }
        
        // d 배열 초기화: 2개의 상태(현재, 이전)와 5x5 (두 발의 위치: 0~4, 0은 중앙)
        d = new int[2][5][5];
        // 0: 현재 상태, 1: 이전 상태
        // 모든 상태를 매우 큰 값(Integer.MAX_VALUE)으로 초기화
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                d[0][i][j] = Integer.MAX_VALUE;
                d[1][i][j] = Integer.MAX_VALUE;
            }
        }
        // 초기 상태: 두 발 모두 중앙(0)에 있으므로 비용은 0
        d[1][0][0] = 0;
        
        int pos; // 현재 진행하는 방향을 저장할 변수
        // 입력된 모든 동작에 대해 DP 진행 (1부터 n까지)
        for (int k = 1; k <= n; k++) {
            pos = arr[k]; // 이번 동작에서 눌러야 하는 패드 번호

            // 이전 상태의 모든 발 위치에 대해 반복
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    // 이전 상태가 유효하지 않으면 넘어감
                    if (d[1][i][j] == Integer.MAX_VALUE) {
                        continue;
                    }
                    // 왼쪽 발을 움직이는 경우:
                    // 현재 상태에서 왼쪽 발을 pos로 옮기고, 오른쪽 발은 j 그대로.
                    // 이전 상태에서 i에서 pos로 이동하는 비용을 더함.
                    d[0][pos][j] = Math.min(d[0][pos][j], d[1][i][j] + cost(i, pos));
                    
                    // 오른쪽 발을 움직이는 경우:
                    // 현재 상태에서 오른쪽 발을 pos로 옮기고, 왼쪽 발은 i 그대로.
                    // 이전 상태에서 j에서 pos로 이동하는 비용을 더함.
                    d[0][i][pos] = Math.min(d[0][i][pos], d[1][i][j] + cost(j, pos));
                }
            }

            // 현재 상태(d[0])를 이전 상태(d[1])로 복사하고, d[0]은 다시 초기화
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    d[1][i][j] = d[0][i][j];
                    d[0][i][j] = Integer.MAX_VALUE;
                }
            }
        }
        
        // 최종 비용(ans) 초기화: 매우 큰 값으로 시작
        int ans = Integer.MAX_VALUE;
        // 마지막 동작에서 눌러야 한 패드 번호는 arr[n]임.
        // 두 발 중 하나가 마지막 번호에 위치한 모든 경우 중 최소 비용을 찾음.
        for (int i = 0; i < 5; i++) {
            ans = Math.min(ans, d[1][arr[n]][i]); // 왼쪽 발이 arr[n]인 경우
            ans = Math.min(ans, d[1][i][arr[n]]); // 오른쪽 발이 arr[n]인 경우
        }
        // 최종 최소 비용 출력
        System.out.println(ans);
    }
    
    /**
     * from 위치에서 to 위치로 이동할 때의 비용을 계산하는 함수
     * @param from 현재 발의 위치 (0: 중앙, 1~4: 주변 패드)
     * @param to 이동할 위치
     * @return 이동에 드는 비용
     */
    static int cost(int from, int to) {
        // 중앙(0)에서 다른 패드로 이동할 때는 2의 비용
        if (from == 0) {
            return 2;
        }
        // 같은 위치로 이동하는 경우는 1의 비용 (즉, 제자리)
        if (from == to) {
            return 1;
        }
        // 대각선 혹은 반대쪽으로 이동하는 경우: from과 to의 합이 짝수면 4의 비용
        if ((from + to) % 2 == 0) {
            return 4;
        }
        // 그 외의 경우에는 3의 비용
        return 3;
    }
}
