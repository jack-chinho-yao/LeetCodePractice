package leetcode2026.p981timebasedkeyvaluestore;

import java.util.HashMap;

public class P981V1TimeBasedKeyValueStoreNaiveSolution {

    // Map<key, list of (timestamp, value)>, get() scans the list. set O(1), get O(n).
    class TimeMap {

        TimeMap() {
            // TODO
        }

        public void set(String key, String value, int timestamp) {
            // TODO
        }

        public String get(String key, int timestamp) {
            // TODO
            return "";
        }
    }

    public static void main(String[] args) {
        TimeMap tm = new P981V1TimeBasedKeyValueStoreNaiveSolution().new TimeMap();
        tm.set("foo", "bar", 1);
        System.out.println(tm.get("foo", 1));   // "bar"
        System.out.println(tm.get("foo", 3));   // "bar"   (latest with ts <= 3)
        tm.set("foo", "bar2", 4);
        System.out.println(tm.get("foo", 4));   // "bar2"
        System.out.println(tm.get("foo", 5));   // "bar2"
        System.out.println(tm.get("foo", 0));   // ""      (nothing with ts <= 0)
    }

    /*
    Problem 981. Time Based Key-Value Store

    Design a time-based key-value data structure that can store multiple values
    for the same key at different timestamps, and retrieve the value for a key at
    a given timestamp.

    Implement:
    - TimeMap()                              initializes the object.
    - void set(String key, String value, int timestamp)
          stores value with key at time timestamp.
    - String get(String key, int timestamp)
          returns the value set for `key` with the LARGEST timestamp_prev such
          that timestamp_prev <= timestamp. If none exists, returns "".

    Important guarantee: all set() calls for a given key arrive with strictly
    INCREASING timestamps. (This is the property V2 will exploit.)

    Example:
        set("foo","bar",1); get("foo",1) -> "bar"; get("foo",3) -> "bar";
        set("foo","bar2",4); get("foo",4) -> "bar2"; get("foo",5) -> "bar2".

    Naive approach:
    - Store Map<String, List<(timestamp, value)>>.
    - set: append (timestamp, value) to the key's list — O(1).
    - get: walk the key's list and keep the value whose timestamp is the largest
      one still <= the query timestamp — O(n) per get.
    - This works but get is linear. V2 makes get O(log n).
    */
}
