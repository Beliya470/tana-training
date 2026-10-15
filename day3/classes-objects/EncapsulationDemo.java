public class EncapsulationDemo {
    public static void main(String[] args) {
        Person person = new Person();

        // person.name = "John";  // this would not compile, name is private
        person.setName("John");

        String n = person.getName();
        System.out.println(n);
    }
}
