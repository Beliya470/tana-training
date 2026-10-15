package accessmodifiersdemo2;

import accessmodifiersdemo.ClassA;

// ClassD extends ClassA, so protected members can go outside the package
public class ClassD extends ClassA {
    public static void main(String[] args) {
        ClassD d = new ClassD();

        System.out.println(d.protectedId);  // ok: inheritance
        d.printProtectedId();               // ok: inheritance
        System.out.println(d.publicId);     // ok
        // d.defaultId;                     // error: default does not leave the package
    }
}
