# Day 4 notes: inheritance and interfaces

Short notes from the four Udemy lessons (OOPS introduction, Inheritance, Method overriding, Abstraction and interface). Simple words.

## OOPS introduction
- OOPS means Object Oriented Programming System. Java follows it.
- If I follow the OOPS rules, the code is easier to read and to maintain.
- There are four pillars: inheritance, polymorphism, abstraction and encapsulation.

## Inheritance
- Inheritance means a child class gets things from a parent class, like a child uses what a parent has.
- The problem it solves: `Student` and `Professor` both need name, age and address. Without inheritance I write the same variables and the same print method in both classes. That is duplicate work.
- The fix: put the common code in one class (`Person`) and make the other classes extend it. Student and Professor only keep what is special to them (course name and fees, or salary and experience).
- The main reason is code reusability: write once, use in many classes.
- It is an is-a relationship: a Student is a Person. A Professor is a Person.
- The keyword is `extends`: `class Student extends Person`.
- Parent class = Person. Child class = Student.
- Everything in the parent (variables and methods) is also in the child. In the slide example, `Employee extends Person` has two variables: `department` and `name`. `name` is not written in `Employee`, it comes from `Person`.
- `Object` is the parent of every class that does not say `extends`. `Plain` has no `extends`, so its parent is `java.lang.Object`. `Person` also has no `extends`, so `Student` is a child of `Person`, and `Person` is a child of `Object`.

## Method overriding
- Overriding needs inheritance: at least two classes with a parent and child.
- The child gets the parent's methods. If I am not happy with how a parent method works, I write the same method in the child with my own code inside.
- The name and the signature stay the same. Only the code inside the curly brackets changes.
- I do not change the parent, because the parent code is the general version and other children (like `Cat`) may need it as it is.
- If the child has the method, Java runs it. If not, Java goes up to the parent and runs that one. It depends on which object I made: `new Dog()` runs the Dog version, `new Animal()` runs the Animal version.
- This is also called runtime polymorphism, because Java decides which method to run when the program runs, from the object.
- A static method cannot be overridden. So the `main` method cannot be overridden, because it is static. When I wrote `@Override` on a static method the compiler said: `static methods cannot be annotated with @Override`.
- `@Override` was not in the lesson. I added it because it is an annotation, and it makes the compiler check that I really override a method.

## Abstraction and interface
- Abstraction means hiding the inside details and only giving the service. Example: I dial a number on a phone and it calls. I do not know what happens inside.
- In Java there are two ways: an abstract class or an interface. This lesson was about the interface (I skip abstract class for now).
- An interface is like a blueprint of a class. It is not a class.
- An interface only defines the services. The methods have a name but no body. They are abstract methods.
- If I write a body in an interface method I get an error: `interface abstract methods cannot have body`.
- A class uses an interface with `implements`. It is also an is-a relationship. The interface is the parent and the class is the child.
- The class MUST give the body of all the abstract methods. If it misses one, the error does not go: `Book is not abstract and does not override abstract method m1() in Interface2`.
- One class can implement many interfaces, separated by a comma: `class Book implements Printable, Interface2`. But a class can extend only one class.
- Every method in an interface is `public abstract` by default, even if I do not write the words.
- Every variable in an interface is `public static final` by default. So I can use it with the interface name: `Printable.name`.
- I cannot make an object of an interface: `Printable is abstract; cannot be instantiated`.
- An interface has no constructor, because there is no object to make.
- An interface can have a static method with a body. I call it with the interface name and I do not need an object.
- With an interface I can get 100 percent abstraction, because all the methods are abstract.

## Why List is an interface and ArrayList is a class
- `List` is an interface. It only says what a list can do (`add`, `get`, `remove`, `size`) and has no code for it. I cannot do `new List<String>()`: `List is abstract; cannot be instantiated`.
- `ArrayList` is a class that implements `List`. It has the real code, so I can make an object of it with `new`. `LinkedList` also implements `List`.
- This is the same as `Book implements Printable`: the interface is the parent and the class is the child.
- That is why I write `List<String> names = new ArrayList<String>();`. The variable type is the interface and the object is made from the class. If I change to `new LinkedList<String>()`, the rest of my code works the same.

## Words to remember
- `extends`: a class takes from a class.
- `implements`: a class takes from an interface.
- override: write the same method again in the child, with a new body.
- abstract method: a method with no body.
