import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {

    // N: 입력받은 원소의 개수, tree: 세그먼트 트리 배열 (구간 합 계산용)
    static int N, tree[];
    // arr: 각 원소를 {원래 인덱스, 값 또는 나중에 등수로 변환된 값} 형태로 저장하는 리스트
    static ArrayList<int[]> arr;

    public static void main(String[] args) throws IOException {
        // 빠른 입력을 위한 BufferedReader와 StringBuilder 준비
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        // 원소의 개수 N 입력
        N = Integer.parseInt(br.readLine());
        arr = new ArrayList<int[]>();
        // 원소들을 입력받으며 원래 인덱스와 함께 저장
        for (int i = 0; i < N; i++) {
            arr.add(new int[] { i, Integer.parseInt(br.readLine()) });
        }
        
        // ★ 내림차순 정렬: 값이 큰 원소가 앞에 오도록 정렬
        Collections.sort(arr, (o1, o2) -> {
            return o2[1] - o1[1];
        });
        
        // 정렬 후 각 원소에 대해 "등수"를 부여
        // 가장 큰 값은 0, 그 다음은 1, ... 식으로 부여
        for (int i = 0; i < N; i++) {
            arr.get(i)[1] = i;
        }
        
        // 원래 순서(입력 순서)를 복원하기 위해 원래 인덱스 기준으로 재정렬
        Collections.sort(arr, (o1, o2) -> {
            return o1[0] - o2[0];
        });
        
        // 세그먼트 트리 배열 초기화 (최대 원소 개수의 4배 크기)
        tree = new int[N * 4];
        
        // 원래 입력 순서대로 각 원소에 대해 처리
        for (int i = 0; i < N; i++) {
            // 현재 원소의 등수(내림차순 정렬 후 부여된 값)를 가져옴
            int index = arr.get(i)[1];
            // 세그먼트 트리에서 [0, index] 구간에 있는 값의 합(즉, 지금까지 등장한
            // '등수'가 0부터 index 사이인 원소의 개수)를 구함.
            // 여기에 1을 더하면, 현재 원소의 "순위" (1부터 시작)를 구할 수 있음.
            sb.append((query(1, 0, N - 1, 0, index) + 1) + "\n");
            // 현재 원소의 등수가 등장했으므로, 세그먼트 트리에서 해당 인덱스 값을 1 증가시킴.
            update(1, 0, N - 1, index, 1);
        }
        // 결과 출력
        System.out.println(sb.toString());
    }

    /**
     * 세그먼트 트리 업데이트 함수
     * @param node 현재 노드 번호
     * @param start 현재 구간의 시작 인덱스
     * @param end 현재 구간의 끝 인덱스
     * @param idx 업데이트 할 인덱스
     * @param diff 업데이트 할 차이 (이 문제에서는 1)
     */
    static void update(int node, int start, int end, int idx, int diff) {
        // 업데이트할 인덱스가 현재 구간에 포함되지 않으면 바로 리턴
        if (idx < start || end < idx) {
            return;
        }
        // 현재 노드 값 업데이트 (diff 만큼 증가)
        tree[node] += diff;
        // 리프 노드가 아니라면 자식 노드들에 대해서도 업데이트 진행
        if (start != end) {
            int mid = (start + end) / 2;
            update(node * 2, start, mid, idx, diff);
            update(node * 2 + 1, mid + 1, end, idx, diff);
        }
    }

    /**
     * 세그먼트 트리 쿼리 함수 (구간 합)
     * @param node 현재 노드 번호
     * @param start 현재 구간의 시작 인덱스
     * @param end 현재 구간의 끝 인덱스
     * @param left 쿼리 구간의 시작 인덱스
     * @param right 쿼리 구간의 끝 인덱스
     * @return 구간 [left, right]에 해당하는 값들의 합
     */
    static int query(int node, int start, int end, int left, int right) {
        // 현재 구간이 쿼리 구간과 완전히 겹치지 않는 경우
        if (right < start || end < left) {
            return 0;
        }
        // 현재 구간이 쿼리 구간에 완전히 포함되는 경우
        if (left <= start && end <= right) {
            return tree[node];
        }
        // 그렇지 않다면, 두 자식 구간으로 나누어 합을 구함
        int mid = (start + end) / 2;
        return query(node * 2, start, mid, left, right)
                + query(node * 2 + 1, mid + 1, end, left, right);
    }

}
