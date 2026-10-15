package accessmodifiers.two;

import accessmodifiers.one.ClassA;

// ClassC extends ClassA, so it can use protected members of ClassA
public class ClassC extends ClassA {
    public static void main(String[] args) {
        ClassC c = new ClassC();
        System.out.println(c.protectedId);  // ok: inheritance
        c.printProtected();                 // ok: inheritance
        System.out.println(c.publicId);     // ok: public
        // c.defaultId -> error: different package
    }
}
