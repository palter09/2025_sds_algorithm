import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[] A;
    static int[] dp; // dp[i] : 길이가 i+1인 증가 부분 수열의 마지막 원소(최소값)

    public static void main(String[] args) throws IOException  {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 입력 받기
        N = Integer.parseInt(br.readLine());
        A = new int[N];
        dp = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }
        
        // dp 배열을 이용한 LIS (Longest Increasing Subsequence) 계산
        int len = 0;
        dp[len++] = A[0]; // 첫 번째 원소로 시작
        for (int i = 1; i < N; i++) {
            if (A[i] > dp[len - 1]) {
                // 현재 수가 dp의 마지막 원소보다 크면, dp에 추가
                dp[len++] = A[i];
            } else {
                // 그렇지 않으면, A[i] 이상의 값이 처음 등장하는 위치를 찾아 갱신
                int idx = lowerBound(dp, 0, len - 1, A[i]);
                dp[idx] = A[i];
            }
        }
        
        // dp의 길이가 곧 최장 증가 부분 수열의 길이
        System.out.println(len);
    }
    
    // lowerBound: arr[left...right] 구간에서 key 이상의 값이 처음 등장하는 인덱스 반환
    static int lowerBound(int[] arr, int left, int right, int key) {
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid] < key) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }
}
