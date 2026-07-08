package oop.generics.example;

// G1: generic class — T is a TYPE PARAMETER, a placeholder filled in at the use
//     site (Box<String>, Box<Integer>...). One class definition serves every
//     concrete type, and the compiler still enforces full type safety.
// G1: 泛型類別 —— T 是「型別參數」，一個在使用端才填入的佔位符
//     (Box<String>、Box<Integer>...)。一份類別定義適用所有實際型別，
//     而編譯器仍然完整保證型別安全。
public class Box<T> {

    // The field's type is the type parameter itself.
    // 欄位的型別就是型別參數本身。
    private T value;

    public Box(T value) {
        this.value = value;
    }

    // Returns exactly T — the caller needs NO cast (compare with old pre-generics
    // code that stored Object and forced a downcast on every get()).
    // 回傳的就是 T —— 呼叫端不需要轉型 (對比舊式泛型前的寫法：存 Object，
    // 每次 get() 都得向下轉型)。
    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Box(" + value + ")";
    }
}
