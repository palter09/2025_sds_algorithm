#include <iostream>
using namespace std;

// 세그먼트 트리(tree)와 지연 업데이트(lazy)를 저장할 전역 배열 포인터
long* tree;
long* lazy;
// 업데이트 및 쿼리를 처리할 전체 배열의 최대 크기 (문제에 따라 조정 가능)
long MAX = 1000000;

// 현재 노드에 저장되어 있는 지연(lazy) 업데이트 값을 자식 노드에 전파하는 함수.
void propagate(long node, long start, long end) {
    if (lazy[node] != 0) {
        // 현재 노드의 구간 합에 지연 업데이트(diff * 구간의 길이)를 적용
        tree[node] += lazy[node] * (end - start + 1);
        // 리프 노드가 아니라면 자식 노드로 지연 업데이트 값을 전파
        if (start != end) {
            lazy[node * 2] += lazy[node];
            lazy[node * 2 + 1] += lazy[node];
        }
        // 현재 노드의 지연 업데이트 값 초기화
        lazy[node] = 0;
    }
}


// 세그먼트 트리에서 구간 합 쿼리를 수행하는 함수 (지연 업데이트 적용).
long lazy_query(long node, long start, long end, long left, long right) {
    // 현재 노드에 대한 지연 업데이트를 먼저 적용
    propagate(node, start, end);
    
    // 현재 구간과 쿼리 구간이 겹치지 않는 경우
    if (right < start || left > end) {
        return 0;
    }
    
    // 현재 구간이 쿼리 구간에 완전히 포함되는 경우
    if (left <= start && right >= end) {
        return tree[node];
    }
    
    // 그렇지 않다면, 구간을 반으로 나누어 자식 노드에서 결과를 구함
    long mid = (start + end) / 2;
    return lazy_query(node * 2, start, mid, left, right) +
           lazy_query(node * 2 + 1, mid + 1, end, left, right);
}


// 세그먼트 트리에서 구간 [left, right]에 diff 값을 더하는 범위 업데이트 함수 (지연 업데이트 사용).
void lazy_update(long node, long start, long end, long left, long right, long diff) {
    // 현재 노드에 대한 지연 업데이트를 먼저 적용
    propagate(node, start, end);
    
    // 현재 구간과 업데이트 구간이 겹치지 않는 경우
    if (right < start || left > end) {
        return;
    }
    
    // 현재 구간이 업데이트 구간에 완전히 포함되는 경우
    if (left <= start && right >= end) {
        // 현재 노드의 구간 합에 diff * 구간 길이를 더해줌
        tree[node] += diff * (end - start + 1);
        // 리프 노드가 아니라면, 자식 노드에 지연 업데이트 값을 저장
        if (start != end) {
            lazy[node * 2] += diff;
            lazy[node * 2 + 1] += diff;
        }
        return;
    }
    
    // 구간이 일부만 겹치는 경우, 자식 노드에 대해 재귀 호출
    long mid = (start + end) / 2;
    lazy_update(node * 2, start, mid, left, right, diff);
    lazy_update(node * 2 + 1, mid + 1, end, left, right, diff);
    // 자식 노드의 결과를 합산하여 현재 노드의 값을 갱신
    tree[node] = tree[node * 2] + tree[node * 2 + 1];
}

void update(long node, long start, long end, long idx, long diff) {
    // 현재 노드의 구간에 idx가 포함되지 않으면 종료
    if (start > idx || end < idx) {
        return;
    }
    
    // 현재 노드의 값을 diff만큼 업데이트
    tree[node] += diff;
    
    // 리프 노드가 아니라면 자식 노드에 대해 재귀 호출
    if (start != end) {
        long mid = (start + end) / 2;
        update(node * 2, start, mid, idx, diff);
        update(node * 2 + 1, mid + 1, end, idx, diff);
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int N, M, K;
    // N: 배열의 크기, M: 업데이트 연산의 수, K: 구간 합 쿼리의 수
    cin >> N >> M >> K;
    
    // 세그먼트 트리와 지연 업데이트 배열 메모리 할당 (최대 크기 4배 할당)
    tree = new long[MAX * 4];
    lazy = new long[MAX * 4];
    // 트리 배열 초기화 (모든 값을 0으로 세팅)
    fill(tree, tree + MAX * 4, 0);
    fill(lazy, lazy + MAX * 4, 0); // lazy 배열도 초기화
    
    // 초기 배열 값 입력 및 세그먼트 트리에 단일 업데이트로 반영
    for (int i = 0; i < N; i++) {
        long num;
        cin >> num;
        update(1, 0, MAX - 1, i, num);
    }
    
    // M+K 개의 연산 처리
    for (int i = 0; i < M + K; i++) {
        int a;
        cin >> a;
        
        // a == 1: 구간 업데이트 연산
        if (a == 1) {
            long b, c, d;
            cin >> b >> c >> d;
            // 입력 받은 b, c를 인덱스에 맞게 (0-indexed) 변환 후 lazy_update 호출
            lazy_update(1, 0, MAX - 1, b - 1, c - 1, d);
        }
        
        // a == 2: 구간 합 쿼리 연산
        if (a == 2) {
            long b, c;
            cin >> b >> c;
            // 입력 받은 b, c를 인덱스에 맞게 (0-indexed) 변환 후 lazy_query 호출하고 결과 출력
            cout << lazy_query(1, 0, MAX - 1, b - 1, c - 1) << endl;
        }
    }
    
    // 동적 할당한 메모리 해제
    delete[] tree;
    delete[] lazy;
    return 0;
}
