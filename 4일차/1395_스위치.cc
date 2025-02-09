#include <iostream>
#include <vector>
using namespace std;

int N, Q;
vector<int> seg;        // 각 구간별 켜진 스위치의 개수를 저장합니다.
vector<bool> lazy;      // lazy[idx]가 true면 해당 노드를 나중에 반전 처리해야 함을 의미

// lazy 플래그가 설정된 구간에 대해 실제 값을 갱신하는 함수
void propagate(int idx, int s, int e) {
    if(lazy[idx]) {
        // 현재 구간 [s,e]의 스위치 개수를 반전합니다.
        // 즉, 켜진 개수가 (구간 길이 - 기존 켜진 개수)로 바뀝니다.
        seg[idx] = (e - s + 1) - seg[idx];
        if(s != e) { // 리프 노드가 아니라면 자식에게 lazy 플래그 전달
            lazy[idx * 2] = !lazy[idx * 2];
            lazy[idx * 2 + 1] = !lazy[idx * 2 + 1];
        }
        lazy[idx] = false; // 현재 노드는 처리가 끝났으므로 초기화
    }
}
 
// 구간 [l, r]에 대해 스위치 상태를 토글하는 함수 (업데이트)
void update(int idx, int s, int e, int l, int r) {
    // lazy 처리: 현재 노드에 보류된 작업이 있다면 먼저 처리
    propagate(idx, s, e);
    
    // 현재 구간 [s,e]가 [l, r]과 아예 겹치지 않으면 리턴
    if(e < l || s > r) return;
    
    // 현재 구간이 [l, r]에 완전히 포함된다면
    if(l <= s && e <= r) {
        // lazy 플래그를 설정한 뒤 바로 처리
        lazy[idx] = true;
        propagate(idx, s, e);
        return;
    }
    
    int mid = (s + e) / 2;
    update(idx * 2, s, mid, l, r);
    update(idx * 2 + 1, mid + 1, e, l, r);
    // 자식 노드를 업데이트한 후, 현재 노드 값을 갱신
    seg[idx] = seg[idx * 2] + seg[idx * 2 + 1];
}
 
// 구간 [l, r]에서 켜진 스위치의 개수를 반환하는 쿼리 함수
int query(int idx, int s, int e, int l, int r) {
    // lazy 처리
    propagate(idx, s, e);
    
    // 구간 [s,e]가 [l, r]과 전혀 겹치지 않으면
    if(e < l || s > r) return 0;
    
    // 현재 구간이 [l, r]에 완전히 포함되면
    if(l <= s && e <= r) return seg[idx];
    
    int mid = (s + e) / 2;
    return query(idx * 2, s, mid, l, r) + query(idx * 2 + 1, mid + 1, e, l, r);
}
 
int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    cin >> N >> Q;
    // 세그먼트 트리와 lazy 배열의 크기를 4*N으로 잡습니다.
    seg.resize(4 * N, 0);
    lazy.resize(4 * N, false);
    
    // 초기 상태는 모든 스위치가 꺼져있으므로 seg 배열은 모두 0입니다.
    // 별도의 빌드(build) 과정은 필요하지 않습니다.
    
    while(Q--) {
        int op, a, b;
        cin >> op >> a >> b;
        // 문제에 따르면,
        // op == 0 : 구간 [a, b]의 스위치를 토글(업데이트)
        // op == 1 : 구간 [a, b]에서 켜진 스위치의 개수를 쿼리
        if(op == 0) {
            update(1, 1, N, a, b);
        } else { // op == 1
            cout << query(1, 1, N, a, b) << "\n";
        }
    }
    
    return 0;
}
