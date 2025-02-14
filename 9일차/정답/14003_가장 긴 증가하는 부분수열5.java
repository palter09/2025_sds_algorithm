import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    // 입력된 수열을 저장 (인덱스 1부터 사용)
    static int arr[];
    // d[i]: 길이가 i인 증가 부분수열의 마지막 원소들 중 가장 작은 값을 저장
    static int d[];
    // path[i]: arr[i]가 증가 부분수열에서 몇 번째 위치에 들어가는지를 기록
    static int path[];
    // ans[i]: 재구성한 최종적인 가장 긴 증가 부분수열(LIS)을 저장 (인덱스 1부터)
    static int ans[];
    // len: 현재까지 발견된 가장 긴 증가 부분수열의 길이
    static int len;
    // N: 수열의 길이
    static int N;
    // d 배열의 최대 크기 (충분히 큰 값으로 초기화)
    static int MAX = 1000000;

    public static void main(String[] args) throws IOException {
        // 입력을 빠르게 받기 위한 BufferedReader와 StringTokenizer 사용
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 결과를 누적해서 출력하기 위한 StringBuilder
        StringBuilder sb = new StringBuilder();

        // 첫 줄에서 수열의 길이 N 읽기
        N = Integer.parseInt(st.nextToken());
        // 배열들을 크기 N 또는 MAX에 맞게 초기화 (인덱스 1부터 사용)
        arr = new int[N + 1];
        d = new int[MAX + 1];
        path = new int[N + 1];
        ans = new int[N + 1];

        // 두 번째 줄에서 수열의 원소들을 읽어서 arr 배열에 저장 (인덱스 1부터)
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        
        // d 배열을 모두 Integer.MAX_VALUE로 초기화
        // 아직 어떤 길이의 증가 부분수열도 구성되지 않았음을 의미
        for (int i = 0; i <= MAX; i++) {
            d[i] = Integer.MAX_VALUE;
        }

        int idx;
        // 각 원소에 대해 가장 긴 증가 부분수열(LIS)을 구성하는 과정
        for (int i = 1; i <= N; i++) {
            // arr[i]가 들어갈 수 있는 가장 작은 위치(부분수열의 길이)를 이진 탐색으로 찾음
            idx = lowerBound(arr[i]);
            // 해당 길이(idx)의 증가 부분수열의 마지막 원소를 arr[i]로 업데이트
            d[idx] = arr[i];
            // 현재 원소가 증가 부분수열의 몇 번째 원소인지를 기록
            path[i] = idx;
            // 가장 긴 부분수열의 길이를 갱신
            len = Math.max(len, idx);
        }
        
        // 결과에 가장 긴 증가 부분수열의 길이를 추가
        sb.append(len + "\n");
        
        // 재구성을 위한 초기화
        // x는 재구성할 때 현재까지 선택한 수보다 작은 값이어야 함
        int x = Integer.MAX_VALUE;
        // idx는 재구성할 때 필요한 증가 부분수열의 길이 (뒤에서부터 복원)
        idx = len;
        // 수열을 뒤에서부터 순회하면서 실제 LIS를 재구성
        for (int i = N; i >= 1; i--) {
            // 만약 현재 원소가 길이 idx의 부분수열에 속하며, x보다 작으면 선택
            if (path[i] == idx && arr[i] < x) {
                ans[idx--] = arr[i];  // ans 배열에 저장하고, 다음 위치로 이동
                x = arr[i];           // x 업데이트: 다음 선택 원소는 더 작은 값이어야 함
            }
        }
        
        // 재구성한 LIS를 결과 문자열에 추가
        for (int i = 1; i <= len; i++) {
            sb.append(ans[i] + " ");
        }
        // 최종 결과 출력
        System.out.println(sb.toString());
    }
    
    /**
     * lowerBound 함수
     * d 배열에서 값이 val 이상인 첫 번째 인덱스를 찾는 이진 탐색
     * 이는 arr[i]를 넣어야 할 증가 부분수열의 위치(길이)를 의미
     */
    static int lowerBound(int val) {
        int start = 1;
        int end = MAX;
        int mid;
        while (start < end) {
            mid = (start + end) / 2;
            // d[mid]가 val 이상이면, 가능한 위치이므로 탐색 범위를 줄임
            if (d[mid] >= val) {
                end = mid;
            } else {
                // d[mid]가 val보다 작으면, 더 큰 인덱스를 찾아야 함
                start = mid + 1;
            }
        }
        return start;
    }
}