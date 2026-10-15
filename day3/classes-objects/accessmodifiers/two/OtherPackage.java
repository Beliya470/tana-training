package accessmodifiers.two;

import accessmodifiers.one.ClassA;

public class OtherPackage {
    public static void main(String[] args) {
        ClassA a = new ClassA();

        // a.privateId    -> error: private
        // a.defaultId    -> error: different package
        // a.protectedId  -> error: different package, and no inheritance
        System.out.println(a.publicId);  // ok: public works everywhere
    }
}
