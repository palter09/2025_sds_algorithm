#include <iostream>
#include <algorithm>
using namespace std;

// 세그먼트 트리를 저장할 전역 포인터 (long long 사용)
long long* tree;

// 구간 합을 계산하는 함수
// node: 현재 노드 번호, start ~ end: 현재 노드가 담당하는 구간
// left ~ right: 합을 구하고자 하는 구간
long long query(long long node, long long start, long long end, long long left, long long right) {
    // 구간이 겹치지 않으면 0 반환
    if (start > right || end < left) {
        return 0;
    }
    
    // 현재 구간이 완전히 구하고자 하는 구간에 포함되면 해당 노드의 값을 반환
    if (start >= left && end <= right) {
        return tree[node];
    }
    
    long long mid = (start + end) / 2;
    return query(node * 2, start, mid, left, right) + query(node * 2 + 1, mid + 1, end, left, right);
}

// 특정 인덱스의 값을 수정하는 함수
// node: 현재 노드 번호, start ~ end: 현재 노드가 담당하는 구간
// idx: 수정하고자 하는 배열의 인덱스, diff: 변경된 값(새 값 - 기존 값)
void update(long long node, long long start, long long end, long long idx, long long diff) {
    // 해당 인덱스가 현재 구간에 포함되지 않으면 아무 것도 하지 않음
    if (idx < start || idx > end) {
        return;
    }
    
    // 현재 노드의 값을 diff만큼 갱신
    tree[node] += diff;
    
    // 리프 노드가 아니라면 자식 노드에도 동일하게 업데이트
    if (start != end) {
        long long mid = (start + end) / 2;
        update(node * 2, start, mid, idx, diff);
        update(node * 2 + 1, mid + 1, end, idx, diff);
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    long long N, M, K;
    cin >> N >> M >> K;
    
    // 세그먼트 트리 배열 할당 (최악의 경우 크기는 4*N)
    tree = new long long[N * 4];
    fill(tree, tree + N * 4, 0);
    
    // 배열의 초기값을 입력받으면서 세그먼트 트리 업데이트
    for (long long i = 0; i < N; i++) {
        long long n;
        cin >> n;
        update(1, 0, N - 1, i, n);
    }
    
    // 총 M+K번의 연산 처리
    // a == 1 : update 연산 (b번째 수를 c로 변경)
    // a == 2 : query 연산 (b번째부터 c번째까지의 합)
    for (long long i = 0; i < M + K; i++) {
        int a;
        long long b, c;
        cin >> a >> b >> c;
        
        if (a == 1) {
            // 기존 b번째 값을 구한 후, 변경값(diff)를 계산하여 업데이트
            long long origin = query(1, 0, N - 1, b - 1, b - 1);
            long long diff = c - origin;
            update(1, 0, N - 1, b - 1, diff);
        }
        else if (a == 2) {
            // b번째부터 c번째까지의 구간 합을 출력
            cout << query(1, 0, N - 1, b - 1, c - 1) << "\n";
        }
    }
    
    // 동적 할당한 메모리 해제
    delete[] tree;
    return 0;
}
