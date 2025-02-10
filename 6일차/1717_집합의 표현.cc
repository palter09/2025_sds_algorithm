#include <iostream>
#include <vector>
using namespace std;

// 전역 변수 선언
// p: 각 원소의 부모를 저장하는 벡터 (Union-Find 자료구조에서 사용)
// n: 원소의 개수, m: 연산(쿼리)의 개수
vector<int> p;
int n, m;

// find 함수: 원소 a가 속한 집합의 대표(루트)를 찾는 함수 (경로 압축 적용)
int find(int a) {
    // 만약 a가 자기 자신을 부모로 가지고 있다면, a는 루트이다.
    if (a == p[a])
        return a;
    
    // 경로 압축: a의 부모를 재귀적으로 찾은 루트로 업데이트하여 이후의 탐색을 빠르게 함
    p[a] = find(p[a]);
    return p[a];
}

// uni 함수: 두 원소 a와 b가 속한 집합을 하나로 합치는 union 연산 함수
void uni(int a, int b) {
    // a와 b 각각의 집합 대표(루트)를 찾음
    int X = find(a);
    int Y = find(b);
    
    // 두 원소가 서로 다른 집합에 속해 있다면 합쳐준다.
    if (X != Y) {
        // 여기서는 b의 루트를 a의 루트에 연결하여 두 집합을 합침
        p[Y] = X;
    }
}

int main() {
    // 입출력 속도 향상을 위한 설정
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    // 원소의 개수 n과 연산(쿼리)의 개수 m을 입력받음
    cin >> n >> m;
    // 벡터 p의 크기를 n+1로 조정 (원소를 0부터 n까지 사용)
    p.resize(n + 1);
    
    // 초기화: 각 원소는 자기 자신을 부모로 가지므로, 모두 별개의 집합에 속함
    for (int i = 0; i <= n; i++) {
        p[i] = i;
    }
    
    // m번의 연산(쿼리)을 수행
    for (int i = 0; i < m; i++) {
        int num, a, b;
        // 연산의 종류(num)와 두 원소 a, b를 입력받음
        cin >> num >> a >> b;
        
        // num이 0이면 union 연산: a와 b가 속한 집합을 합침
        if (num == 0)
            uni(a, b);
        // num이 1이면 find 연산: a와 b가 같은 집합에 속해 있는지 확인
        else if (num == 1) {
            // find 함수를 이용하여 두 원소의 대표(루트)를 비교
            if (find(a) == find(b))
                cout << "YES" << "\n";  // 같은 집합이면 "YES" 출력
            else
                cout << "NO" << "\n";   // 다른 집합이면 "NO" 출력
        }
    }
    
    return 0;
}
