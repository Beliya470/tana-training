package accessmodifiersdemo;

// ClassB is in the same package as ClassA
public class ClassB {
    public static void main(String[] args) {
        ClassA cl = new ClassA();

        // cl.privateId;            // error: private is not visible
        // cl.printPrivateId();     // error: private is not visible
        System.out.println(cl.defaultId);   // ok, same package
        cl.printDefaultId();                // ok, same package
        System.out.println(cl.protectedId); // ok, same package
        cl.printProtectedId();              // ok, same package
        System.out.println(cl.publicId);    // ok
        cl.printPublicId();                 // ok

        // new PrivateConstructorClass(5);  // error: the constructor is not visible
    }
}
