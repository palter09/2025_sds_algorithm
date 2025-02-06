#include <iostream>
#include <algorithm>
using namespace std;

const int MAX = 1000000;  // 맛의 개수(1~1,000,000)
int tree[4 * MAX];        // segment tree 배열

// idx(맛 번호의 인덱스 0~MAX-1)에 있는 사탕 개수를 diff만큼 변화시킵니다.
void update(int node, int start, int end, int idx, int diff) {
    if (idx < start || idx > end)
        return;
    
    tree[node] += diff;
    if (start != end) {
        int mid = (start + end) / 2;
        update(node * 2, start, mid, idx, diff);
        update(node * 2 + 1, mid + 1, end, idx, diff);
    }
}

// segment tree를 이용하여 현재 사탕 개수 누적합에서 k번째 사탕이 위치한 맛(인덱스)을 찾습니다.
int findCandy(int node, int start, int end, int k) {
    if (start == end)
        return start; // leaf에 도달하면 해당 맛의 인덱스를 반환
    
    int mid = (start + end) / 2;
    // 왼쪽 자식의 누적합이 k 이상이면 왼쪽으로, 아니면 오른쪽으로 이동하며 k에서 왼쪽 누적합을 빼줌
    if (tree[node * 2] >= k)
        return findCandy(node * 2, start, mid, k);
    else
        return findCandy(node * 2 + 1, mid + 1, end, k - tree[node * 2]);
}

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int n;
    cin >> n;
    // tree 배열을 0으로 초기화
    fill(tree, tree + 4 * MAX, 0);
    
    while(n--) {
        int op;
        cin >> op;
        if(op == 1) {
            // 1 B 연산: 사탕 상자에서 누적 순위가 B번째인 사탕의 맛 번호를 구하고 제거
            int k;
            cin >> k;
            // findCandy는 0~MAX-1 인덱스 값을 반환하므로 실제 맛 번호는 +1
            int tasteIdx = findCandy(1, 0, MAX - 1, k);
            cout << tasteIdx + 1 << "\n";
            update(1, 0, MAX - 1, tasteIdx, -1);  // 해당 맛의 사탕 하나 제거
        } else {
            // 2 B C 연산: 맛 번호 B의 사탕 개수를 C만큼 변경
            int taste, num;
            cin >> taste >> num;
            // 입력 맛 번호는 1부터 시작하므로 인덱스로 사용하기 위해 1을 빼줌
            update(1, 0, MAX - 1, taste - 1, num);
        }
    }
    
    return 0;
}
