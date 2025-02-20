#include <iostream>
#include <vector>
#include <string>
#include <algorithm>

using namespace std;

int n, m;
vector<string> DNA;
vector<string> superDNA; // 크기: 2^n
vector<int> dp;        // dp[i] = answer for subset i, 초기값은 n+1, dp[0]=0

// 두 DNA 문자열을 병합하는 함수 (유효하지 않으면 빈 문자열("") 반환)
string mergeDNA(const string &s1, const string &s2) {
    if(s1.empty() || s2.empty()) return "";
    string res(m, ' ');
    for (int i = 0; i < m; i++) {
        if(s1[i] == '.')
            res[i] = s2[i];
        else if(s2[i] == '.')
            res[i] = s1[i];
        else if(s1[i] == s2[i])
            res[i] = s1[i];
        else
            return "";
    }
    return res;
}

// index에 해당하는 subset의 superDNA를 재귀적으로 생성
void genSuperDNA(int index) {
    int loc = 0;
    int tempIndex = index;
    // 오른쪽에서 첫번째 1의 위치 찾기
    while(tempIndex % 2 == 0) {
        tempIndex /= 2;
        loc++;
    }
    int pow2 = 1 << loc; // 2**loc
    // DNA[loc] : 오른쪽에 위치한 단일 원소 서열, superDNA[index - pow2] : 나머지 서열
    superDNA[index] = mergeDNA(DNA[loc], superDNA[index - pow2]);
}

// dp를 재귀적으로 구하는 함수
int genAnswer(int index) {
    // 이미 계산된 경우 (dp[index]가 n+1 미만이면)
    if(dp[index] < n + 1) return dp[index];
    
    // index에 포함된 DNA의 번호(비트가 1인 위치)를 추출
    vector<int> bits;
    int temp = index;
    for (int i = 0; i < n; i++) {
        if(temp & 1)
            bits.push_back(i);
        temp >>= 1;
    }
    int k = bits.size();
    // k개 중 절반만 선택하는 방식: 첫번째 원소(bit들 중 가장 낮은 번호)를 항상 group2에 두고 나머지로만 부분집합을 만든다.
    int subsetCount = 1 << (k - 1);
    for (int mask = 1; mask < subsetCount; mask++) {
        int subset1 = 0;
        // bits[0..k-2]에 대해 mask에 따라 subset1을 구성
        for (int j = 0; j < k - 1; j++) {
            if(mask & (1 << j))
                subset1 |= (1 << bits[j]);
        }
        int subset2 = index - subset1; // 전체에서 subset1을 제외한 나머지
        int candidate = genAnswer(subset1) + genAnswer(subset2);
        dp[index] = min(dp[index], candidate);
    }
    return dp[index];
}

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    cin >> n >> m;
    DNA.resize(n);
    for (int i = 0; i < n; i++) {
        cin >> DNA[i];
    }
    
    int total = 1 << n; // 2^n
    superDNA.resize(total);
    dp.assign(total, n + 1);
    
    // 기본 superDNA: 공백이 아닌 '.'만 있는 m길이 문자열
    superDNA[0] = string(m, '.');
    dp[0] = 0;
    
    // 모든 부분집합에 대해 superDNA를 생성
    for (int i = 1; i < total; i++) {
        genSuperDNA(i);
    }
    
    // 직접 병합이 가능한 경우 answer = 1, 아니라면 재귀적 분할을 통해 계산
    for (int i = 1; i < total; i++) {
        if(!superDNA[i].empty())
            dp[i] = 1;
        else
            genAnswer(i);
    }
    
    cout << dp[total - 1] << "\n";
    return 0;
}
