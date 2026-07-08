package leetcode2026.p853carfleet;

import java.util.Map;
import java.util.TreeMap;

public class P853V1CarFleetNaiveSolution {

    // Naive approach - TODO: fill in. (think: simulate / compare arrival times)
    public int carFleet(int target, int[] position, int[] speed) {
        // TODO: implement
        int fleet = 0;
        int length = position.length;
        TreeMap<Integer, Double> positionTime = new TreeMap();
        for(int i = 0; i < length; i++){
            positionTime.put(position[i], (double)(target - position[i]) / speed[i]);
        }

        System.out.println("test");
        Map.Entry<Integer, Double> currentFleet = null;
        Map.Entry<Integer, Double> lastEntry = positionTime.lastEntry();
        for (Map.Entry<Integer, Double> integerDoubleEntry : positionTime.entrySet()) {
            if (currentFleet == null) {
                currentFleet = integerDoubleEntry;
                continue;
            }

            if (currentFleet.getValue() >= integerDoubleEntry.getValue()){
                fleet ++;

                if (integerDoubleEntry == lastEntry){
                    fleet ++;
                }
            }


            currentFleet = integerDoubleEntry;
        }


        return fleet == 0 ? 1 : fleet;
    }

    public static void main(String[] args) {
        P853V1CarFleetNaiveSolution solution = new P853V1CarFleetNaiveSolution();

        // Example 1: expected 3
//        System.out.println(solution.carFleet(12, new int[]{10, 8, 0, 5, 3}, new int[]{2, 4, 1, 1, 3}));
        // Example 2: expected 1
//        System.out.println(solution.carFleet(10, new int[]{3}, new int[]{3}));
        // Example 3: expected 1
//        System.out.println(solution.carFleet(100, new int[]{0, 2, 4}, new int[]{4, 2, 1}));

        System.out.println(solution.carFleet(10, new int[]{6,8}, new int[]{3, 2}));
    }
}

/*
    ============================================================================
    LeetCode 853. Car Fleet
    ============================================================================

    n 台車在一條往 target 的單向道路上。第 i 台車起點在 position[i]，速度 speed[i]。

    規則：
      - 車子不能超車。若後車追上前車，它會「卡」在前車後面，並以前車的速度前進，
        兩台車（之後）視為同一個「車隊 (fleet)」一起抵達 target。
      - 一台車自己也算一個車隊。
      - 在「終點 target」剛好相遇也算同一個車隊。

    回傳：抵達 target 的「車隊數量」。

    Example 1:
      target = 12, position = [10,8,0,5,3], speed = [2,4,1,1,3]  -> 3

    Constraints:
      n == position.length == speed.length
      1 <= n <= 1e5
      0 <= position[i] < target <= 1e6
      所有 position 互不相同
      1 <= speed[i] <= 1e6

    ----------------------------------------------------------------------------
    思路提示（自己想，想不出來再看）：
      - 一台車到 target 的「時間」= (target - position) / speed。
      - 關鍵：誰會跟誰合併，跟「位置順序」有關 —— 越靠近 target 的車越前面。
      - 想一想：若把車子「按起點由近到遠」排好，逐一比較到站時間，
        什麼情況代表「後車被前車擋住、併入同一車隊」？什麼情況代表「自成新車隊」？
*/
