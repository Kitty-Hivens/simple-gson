class X {
    int value = 42;
}

class Y {
    String name = "Goldy";
    X x = new X();
    int[] numbers = {1, 2, 3};
}

public class Main {
    static void main() throws Exception {
        Y y = new Y();
        System.out.println(SimpleGSON.toJson(y));
    }
}
