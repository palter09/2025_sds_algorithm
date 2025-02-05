#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;
 
int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int N, H;
    cin >> N >> H;
    
    // down: 바닥에서 솟아오르는 장애물 (석순)
    // up: 천장에서 내려오는 장애물 (종유석)
    vector<int> down, up;
    for (int i = 0; i < N; i++){
        int h;
        cin >> h;
        if (i % 2 == 0)
            down.push_back(h);
        else
            up.push_back(h);
    }
    
    sort(down.begin(), down.end());
    sort(up.begin(), up.end());
    
    int n_down = down.size();
    int n_up = up.size();
    
    int minObstacles = N; // 가능한 최대 장애물 수로 초기화
    int count = 0;        // 최소 장애물 수를 가지는 높이의 개수
    
    // 높이 h (1 ~ H) 에 대해 장애물 수 계산
    for (int h = 1; h <= H; h++){
        // [1] 바닥 장애물(석순)의 경우: 높이가 h 이상인 석순의 개수
        // lower_bound( ... , h) 는 정렬된 배열에서 처음으로 h 이상이 되는 원소의 위치를 찾는다.
        int idx_down = lower_bound(down.begin(), down.end(), h) - down.begin();
        int obstacles_down = n_down - idx_down;
 
        // [2] 천장 장애물(종유석)의 경우:
        // 종유석의 길이가 (H - h + 1) 이상이면 부딪힌다.
        int threshold = H - h + 1;
        int idx_up = lower_bound(up.begin(), up.end(), threshold) - up.begin();
        int obstacles_up = n_up - idx_up;
 
        int total = obstacles_down + obstacles_up;
 
        if(total < minObstacles){
            minObstacles = total;
            count = 1;
        } else if(total == minObstacles){
            count++;
        }
    }
    
    cout << minObstacles << " " << count << "\n";
    return 0;
}
