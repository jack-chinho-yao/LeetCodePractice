package oop.generics.template;

// ============================================================================
// G1: Generic class  (泛型類別)
// ============================================================================
// 目標：讓 Box 能裝「任何型別 T」的一個值，取出時不需要轉型。
// Goal: let Box hold one value of ANY type T, with no cast needed when reading it back.
//
// 要寫出 / What to write:
//   - 一個型別參數 T (class Box<T>)
//     a type parameter T  (class Box<T>)
//   - private 欄位 value，型別為 T
//     a private field `value` of type T
//   - 建構子 Box(T value)
//     a constructor Box(T value)
//   - T get()
//   - void set(T value)
//   - toString() 回傳 "Box(<value>)"
//     toString() returns "Box(<value>)"
//
// 想一想：為什麼用 T 而不是 Object？(提示：呼叫端 get() 後還要不要轉型)
// Think: why use T instead of Object? (Hint: does the caller still need a cast after get()?)
public class Box<T> {

    // TODO: 實作

}
