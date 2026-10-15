package accessmodifiers.one;

public class SamePackage {
    public static void main(String[] args) {
        ClassA a = new ClassA();

        // a.privateId   -> error: private is only for the same class
        System.out.println(a.defaultId);    // ok: same package
        System.out.println(a.protectedId);  // ok: same package
        System.out.println(a.publicId);     // ok: public
    }
}
