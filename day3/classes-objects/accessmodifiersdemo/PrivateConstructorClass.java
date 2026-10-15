package accessmodifiersdemo;

public class PrivateConstructorClass {

    int id;

    // private constructor: no other class can create an object of this class
    private PrivateConstructorClass(int i) {
        id = i;
    }
}
