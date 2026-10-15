package accessmodifiersdemo2;

import accessmodifiersdemo.ClassA;

// ClassC is in a different package
public class ClassC {
    public static void main(String[] args) {
        ClassA cla = new ClassA();

        // cla.privateId;        // error: private
        // cla.defaultId;        // error: default stays inside its package
        // cla.protectedId;      // error: no inheritance here
        System.out.println(cla.publicId);  // ok: public works everywhere
        cla.printPublicId();               // ok
    }
}
