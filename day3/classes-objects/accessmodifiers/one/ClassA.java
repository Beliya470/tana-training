package accessmodifiers.one;

public class ClassA {

    private int privateId = 1;
    int defaultId = 2;
    protected int protectedId = 3;
    public int publicId = 4;

    protected void printProtected() {
        System.out.println("protected method");
    }

    // inside the same class, all four can be used
    public void printAll() {
        System.out.println(privateId + " " + defaultId + " " + protectedId + " " + publicId);
    }
}
