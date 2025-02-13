import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] A = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }
        
        // dp[i] : 길이가 i+1인 증가 부분 수열의 마지막 원소의 값 (최소값)
        // dpIndex[i] : dp[i]에 해당하는 원소가 A의 어느 인덱스에 있었는지 저장
        // prev[i] : A의 i번째 원소가 LIS에서 연결된 바로 이전 원소의 인덱스 (없으면 -1)
        int[] dp = new int[N];
        int[] dpIndex = new int[N];
        int[] prev = new int[N];
        
        // 첫 원소 초기화
        dp[0] = A[0];
        dpIndex[0] = 0;
        prev[0] = -1; // 처음 원소는 이전 원소가 없음
        int len = 1; // 현재까지의 LIS 길이
        
        for (int i = 1; i < N; i++) {
            if (A[i] > dp[len - 1]) {
                // 현재 원소가 기존 수열의 마지막 값보다 크다면, 수열 확장
                prev[i] = dpIndex[len - 1]; // 이전 값의 인덱스 저장
                dp[len] = A[i];
                dpIndex[len] = i;
                len++;
            } else {
                // 현재 원소보다 크거나 같은 값이 처음 등장하는 위치를 이분 탐색으로 찾음
                int pos = lowerBound(dp, 0, len - 1, A[i]);
                dp[pos] = A[i];
                dpIndex[pos] = i;
                // pos가 0이면 이전 원소가 없음, 아니면 pos-1 위치의 인덱스 저장
                prev[i] = (pos == 0) ? -1 : dpIndex[pos - 1];
            }
        }
        
        // dpIndex[len-1]에 저장된 인덱스부터 prev 배열을 따라가며 LIS 복원
        int[] lis = new int[len];
        int index = dpIndex[len - 1];
        for (int i = len - 1; i >= 0; i--) {
            lis[i] = A[index];
            index = prev[index];
        }
        
        // 결과 출력
        StringBuilder sb = new StringBuilder();
        sb.append(len).append("\n");
        for (int num : lis) {
            sb.append(num).append(" ");
        }
        System.out.println(sb);
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