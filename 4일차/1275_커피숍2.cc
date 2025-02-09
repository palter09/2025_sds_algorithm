#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

typedef long long ll;

int N, Q;
vector<ll> arr;
vector<ll> segTree;

// 구간 [l, r]의 합을 구하는 함수
ll query(int node, int start, int end, int l, int r) {
    // 구간이 겹치지 않으면 0 리턴
    if(r < start || end < l)
        return 0;
    // 구간 [start, end]가 구간 [l, r]에 완전히 포함될 때
    if(l <= start && end <= r)
        return segTree[node];
    int mid = (start + end) / 2;
    return query(node * 2, start, mid, l, r) +
           query(node * 2 + 1, mid + 1, end, l, r);
}

// 인덱스 idx의 값을 업데이트하는 함수 (diff 만큼의 차이를 적용)
void update(int node, int start, int end, int idx, ll diff) {
    if(idx < start || idx > end)
        return;
    segTree[node] += diff;
    if(start != end) {
        int mid = (start + end) / 2;
        update(node * 2, start, mid, idx, diff);
        update(node * 2 + 1, mid + 1, end, idx, diff);
    }
}

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    cin >> N >> Q;
    arr.resize(N + 1);
    segTree.resize(4 * (N + 1));
    
    for(int i = 1; i <= N; i++){
        cin >> arr[i];
        update(1, 1, N, i, arr[i]);
    }
    
    // 각 쿼리 처리
    while(Q--){
        int x, y, a;
        ll b;
        cin >> x >> y >> a >> b;
        int left = min(x, y);
        int right = max(x, y);
        // 구간 합 출력
        cout << query(1, 1, N, left, right) << "\n";
        // 업데이트: 기존 값과의 차이를 구해서 세그먼트 트리에 반영
        ll diff = b - arr[a];
        arr[a] = b;
        update(1, 1, N, a, diff);
    }
    
    return 0;
}
