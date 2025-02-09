#include <iostream>
#include <vector>
using namespace std;
 
typedef long long ll;
 
int N, M;
vector<ll> segTree;
 
// 인덱스 idx에 diff만큼 더하는(또는 빼는) 함수
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
 
// 세그먼트 트리에서 누적합이 k 이상이 되는 첫 인덱스(맛 번호)를 찾는 함수
int find(int node, int start, int end, int k) {
    if(start == end)
        return start;
    int mid = (start + end) / 2;
    if(segTree[node * 2] >= k)
        return find(node * 2, start, mid, k);
    else
        return find(node * 2 + 1, mid + 1, end, k - segTree[node * 2]);
}
 
int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
 
    cin >> N;
    // 맛 번호는 1부터 N까지입니다.
    // segTree의 크기를 4*(N+1)로 잡아줍니다.
    segTree.resize(4 * (N + 1), 0);
    
    // 초기 사탕 개수 입력 (맛 1번부터 N번까지)
    // 문제에 따라 초기값이 0인 경우도 있으니 입력으로 주어진다고 가정합니다.
    for(int i = 1; i <= N; i++){
        int army;
        cin >> army;
        update(1, 1, N, i, army);
    }
    
    cin >> M;
    
    // M개의 쿼리를 처리합니다.
    // 각 쿼리는 세 정수 num, i, a 로 주어지는데,
    //  - num == 1 : i번 맛의 사탕 개수를 a만큼 변경
    //  - num == 2 : 전체 사탕을 순서대로 나열했을 때, i번째 사탕의 맛 번호를 출력한 후 해당 맛의 사탕을 1개 제거
    while (M--){
        int num;
        cin >> num;
        
        if(num == 1) {
        	int i, a;
        	cin >> i >> a;
            update(1, 1, N, i, a);
        }
        else if(num == 2) {
        	int i;
        	cin >> i;
            int idx = find(1, 1, N, i);
            cout << idx << "\n";
        }
    }
    
    return 0;
}
