#include <iostream>
#include <vector>
#include <string>
#include <algorithm>
using namespace std;

int N, K, ans = 0;
vector<string> words;
// 각 알파벳을 배웠는지 여부를 저장하는 배열 (false: 배우지 않음, true: 배움)
bool learned[26] = { false };

// DFS 함수: 
// idx: 알파벳 중에서 선택을 고려할 시작 인덱스 (0 ~ 25)
// cnt: 현재까지 배운 문자 개수
void dfs(int idx, int cnt) {
    // 남은 알파벳 개수가 (26 - idx)개인데, 
    // 아직 배워야 하는 문자 (K - cnt)개보다 적으면 더 이상 선택할 수 없으므로 리턴
    if (26 - idx < K - cnt) return;
    
    // 만약 K개의 문자를 모두 선택했다면, 각 단어가 읽힐 수 있는지 확인
    if (cnt == K) {
        int countReadable = 0;
        // 모든 단어에 대해 검사
        for (const string &word : words) {
            bool canRead = true;
            // 단어에 포함된 각 문자가 배운 문자에 포함되어 있는지 확인
            for (char c : word) {
                if (!learned[c - 'a']) { 
                    canRead = false;
                    break;
                }
            }
            if (canRead) countReadable++;
        }
        ans = max(ans, countReadable);
        return;
    }
    
    // 알파벳 A부터 Z까지 순서대로 선택 (이미 배운 문자는 넘어감)
    for (int i = idx; i < 26; i++) {
        if (!learned[i]) {
            learned[i] = true;
            dfs(i + 1, cnt + 1);
            learned[i] = false;
        }
    }
}
 
int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    cin >> N >> K;
    
    // 필수 문자 (a, n, t, i, c)를 가르칠 수 없으면 어떤 단어도 읽을 수 없음
    if (K < 5) {
        cout << 0;
        return 0;
    }
    // 모든 알파벳을 배울 수 있으면 모든 단어를 읽을 수 있음
    if (K == 26) {
        cout << N;
        return 0;
    }
    
    // 필수 문자들을 learned 배열에 미리 표시
    learned['a' - 'a'] = true;
    learned['n' - 'a'] = true;
    learned['t' - 'a'] = true;
    learned['i' - 'a'] = true;
    learned['c' - 'a'] = true;
    
    // 단어들을 입력받되, "anta"와 "tica"를 제외한 중간 부분만 저장합니다.
    for (int i = 0; i < N; i++){
        string s;
        cin >> s;
        // s의 길이에서 앞의 4글자("anta")와 뒤의 4글자("tica")를 제외
        string middle = s.substr(4, s.size() - 8);
        words.push_back(middle);
    }
    
    // DFS 시작: 이미 5개의 필수 문자를 배웠으므로 cnt는 5부터 시작합니다.
    dfs(0, 5);
    
    cout << ans;
    return 0;
}
