import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    // N: 입력받은 배열의 크기
    // arr: 입력받은 값들을 저장하는 배열 (1-indexed로 사용)
    // d: DP 배열, d[0]는 현재 단계, d[1]은 이전 단계의 상태를 저장
    // H: 최대 높이 (문제에서 주어진 최대 한계값, 여기서는 5000)
    // MOD: 결과를 나눌 소수 (1000000007)
    static int N, arr[], d[][], H = 5000, MOD = 1000000007;

    public static void main(String[] args) throws IOException {
        // 입력을 빠르게 받기 위한 BufferedReader 생성
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        // 첫 줄에 배열의 길이 N을 입력받음
        N = Integer.parseInt(br.readLine());
        // 1번 인덱스부터 N번 인덱스까지 사용하기 위해 N+1 크기의 배열 생성
        arr = new int[N+1];
        // dp 배열: 2행 (이전 단계, 현재 단계) x (H+3) 열 (현재 높이 상태)
        // H+3인 이유는 j+1 인덱스를 사용할 때 인덱스 범위를 벗어나지 않도록
        d = new int[2][H+3];

        // 두 번째 줄에 공백으로 구분된 정수들을 입력받아 arr에 저장
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        
        // 배열의 시작과 끝은 반드시 0이어야 함.
        // 만약 첫번째 값 또는 마지막 값이 0보다 크면 조건에 맞지 않으므로 0을 출력 후 종료.
        if (arr[1] > 0 || arr[N] > 0) {
            System.out.println(0);
            return;
        }
        
        // d 배열의 인덱스 설명:
        // d[1]: 이전 단계의 DP 값, d[0]: 현재 단계의 DP 값
        // 초기 상태: 시작 높이가 0인 경우 1가지 방법이 있으므로 d[1][0] = 1
        d[1][0] = 1;
        
        int h;  // 현재 단계에서의 높이(또는 고정된 값)를 저장할 변수
        
        // 2번째 원소부터 N번째 원소까지 순차적으로 DP 진행
        for (int i = 2; i <= N; i++) {
            // 현재 입력 값을 h에 저장.
            // h가 -1이면 해당 위치의 높이가 정해지지 않았음을 의미 (자유롭게 선택 가능)
            h = arr[i];
            
            if (h == -1) { // 현재 값이 정해지지 않은 경우 (자유롭게 선택)
                // 가능한 모든 높이 j에 대해 이전 단계의 상태에서 전이 가능한 경우의 수를 계산
                for (int j = 0; j <= H; j++) {
                    // 현재 높이 j를 만드는 경우: 이전에 높이 j였던 경우 그대로 유지
                    d[0][j] = d[1][j];
                    
                    // 이전에 높이 j+1에서 내려와 j가 된 경우
                    d[0][j] += d[1][j+1];
                    d[0][j] %= MOD;
                    
                    // 이전에 높이 j-1에서 올라와 j가 된 경우 (j가 0보다 큰 경우에만 가능)
                    if (j > 0) {
                        d[0][j] += d[1][j-1];
                        d[0][j] %= MOD;
                    }
                }
            } else { // 현재 값이 고정되어 있는 경우 (h값이 정해짐)
                // 오직 높이 h에 대해서만 DP 값을 업데이트
                // 이전에 높이 h였던 경우 그대로 유지
                d[0][h] = d[1][h];
                
                // 이전에 높이 h+1에서 내려와 h가 된 경우
                d[0][h] += d[1][h+1];
                d[0][h] %= MOD;
                
                // 이전에 높이 h-1에서 올라와 h가 된 경우 (h가 0보다 큰 경우에만 가능)
                if (h > 0) {
                    d[0][h] += d[1][h-1];
                    d[0][h] %= MOD;
                }
            }
            
            // 현재 단계의 DP 결과를 이전 단계 배열(d[1])로 복사하고, d[0]은 초기화
            for (int j = 0; j <= H; j++) {
                d[1][j] = d[0][j];
                d[0][j] = 0;
            }
        }
        
        // 최종적으로 N번째 단계에서 높이가 0이 되는 경우의 수를 출력
        System.out.println(d[1][0]);
    }
}
