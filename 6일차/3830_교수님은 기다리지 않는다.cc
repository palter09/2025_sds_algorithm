#include <iostream>
#include <vector>
using namespace std;

// p[i].first : i의 부모 노드
// p[i].second: i와 부모 노드 사이의 (weight[i] - weight[parent[i]])
vector<pair<int,int>> p;

// find 함수: a의 루트를 찾으면서, 경로 상의 차이(누적 가중치)를 갱신합니다.
int find(int a) {
    if(a == p[a].first) return a;
    int par = p[a].first;
    p[a].first = find(par);
    p[a].second += p[par].second;
    return p[a].first;
}
 
// uni 함수: 두 노드 a, b가 주어지고, "weight[b] - weight[a] = w" 관계가 주어질 때 두 집합을 합칩니다.
void uni(int a, int b, int w) {
    int rootA = find(a);
    int rootB = find(b);
    
    if(rootA == rootB) return;
    // a와 b가 다른 집합에 있다면, b의 루트를 a의 루트에 연결합니다.
    // 이때, b의 루트와 a의 루트 사이의 누적 차이를 맞춰줍니다.
    p[rootB].first = rootA;
    p[rootB].second = p[a].second - p[b].second + w;
}
 
int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    while(true){
        int N, M;
        cin >> N >> M;
        if(N == 0 && M == 0) break;
        
        // 1부터 N까지 노드를 사용하므로, 인덱스를 1부터 사용합니다.
        p.assign(N+1, {0, 0});
        for(int i = 1; i <= N; i++){
            p[i].first = i;
            p[i].second = 0; // 초기에는 자기 자신과의 차이는 0입니다.
        }
        
        for(int i = 0; i < M; i++){
            char op;
            cin >> op;
            if(op == '!'){
                int a, b, w;
                cin >> a >> b >> w;
                // a와 b 사이에 "weight[b] - weight[a] = w" 관계를 설정합니다.
                uni(a, b, w);
            }
            else if(op == '?'){
                int a, b;
                cin >> a >> b;
                // 두 노드가 같은 집합에 속해 있으면, 누적 차이를 이용하여 두 노드의 무게 차이를 계산합니다.
                if(find(a) == find(b))
                    cout << p[b].second - p[a].second << "\n";
                else
                    cout << "UNKNOWN" << "\n";
            }
        }
    }
    
    return 0;
}
