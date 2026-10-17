package polymorphismdemo;

public class Runner {
    public static void main(String[] args) {

        // Cat has no printAnimal() of its own, so the parent method runs
        Cat c1 = new Cat();
        c1.printAnimal();

        // Dog overrides printAnimal(), so Java runs the one in the Dog class
        Dog d1 = new Dog();
        d1.printAnimal();

        // an object of the parent class runs the method of the parent class
        Animal an = new Animal();
        an.printAnimal();

        // the static method is not overridden, it just belongs to the class
        Animal.testMethod();
    }
}
