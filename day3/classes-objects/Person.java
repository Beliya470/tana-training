public class Person {

    // private: nobody outside this class can touch it directly
    private String name;

    // setter: puts a value in
    public void setName(String name) {
        this.name = name;
    }

    // getter: reads the value
    public String getName() {
        return name;
    }
}
