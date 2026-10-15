package accessmodifiersdemo;

public class ClassA {

    // one variable and one method for each access modifier
    private int privateId = 10;
    int defaultId = 10;
    protected int protectedId = 10;
    public int publicId = 10;

    private void printPrivateId() {
        System.out.println(privateId);
    }

    void printDefaultId() {
        System.out.println(defaultId);
    }

    protected void printProtectedId() {
        System.out.println(protectedId);
    }

    public void printPublicId() {
        System.out.println(publicId);
    }

    // inside the same class, everything can be used
    public void printAll() {
        printPrivateId();
        printDefaultId();
        printProtectedId();
        printPublicId();
    }
}
