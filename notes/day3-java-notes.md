# Day 3 notes: classes, objects, lists and maps

My own words, short and simple.

## Key terms

**Class vs object**
A class is the plan. An object is a real thing made from that plan. One class `Dog` can make many dogs, like `Bruno` and `Rex`. Objects are made with `new`.

**Constructor**
A special method that runs when I create an object with `new`. It has the same name as the class and no return type. It is used to set the starting values. If I write none, Java gives me an empty default one.

**this**
`this` means "this object". I use it when a parameter has the same name as a variable, like `this.name = name;`. The left side is the object's variable. The right side is the parameter.

**private vs public**
`private` means only the same class can use it. `public` means any class can use it. I make variables private to protect them, and I make methods public so others can call them.

**Getters**
A public method that gives back a private variable, like `getName()`. A setter does the opposite and puts a value in. Together they are called encapsulation.

**toString()**
A method that turns an object into text. If I do not write it, printing an object shows something like `Dog@1b6d3586`. If I write it, I choose what is shown, like `Dog{name=Bruno, color=Brown}`.

**static vs instance**
An instance variable belongs to one object, and every object has its own copy. A static variable belongs to the class, and all objects share one copy. I use a static one with the class name, like `Student.studentCount`.

**Packages and imports**
A package is a folder that groups classes, like `java.util`. An import lets me use a class from another package without writing the full name, like `import java.util.ArrayList;`.

**enum**
A fixed list of allowed values, like `enum Protocol { TCP, UDP, ICMP }`. It stops me from using a wrong value, because only those names are allowed.

## Short notes from the lessons

**Class and object**
- A class is a container for variables, methods and constructors. You cannot write Java code outside a class.
- To use what is inside a class, I make an object with `new`. The object lives in heap memory.
- One class can make as many objects as I want. Each needs its own reference variable (`emp1`, `emp2`).
- I use the object and a dot to reach things: `emp1.printEmployee()`, `emp1.employeeDepartment`.
- The class with `main` is the one that runs. I usually keep it as a separate class, like `Runner`.

**Instance and static variables**
- Instance variable: written inside the class, outside methods. One copy for each object. Gets a default value if I do not set one (null, 0, false).
- Static variable: has the word `static`. One copy for the whole class. I use it with the class name.

**Constructors**
- Default constructor: no parameters. Java adds it only if I write no constructor at all. It gives default values (null for String, 0 for numbers).
- Parameterized constructor: takes values, so each object can start with different data. I write it myself.
- Once I write my own constructor, Java does not add the default one.
- I can write many constructors, but their parameters must be different. A duplicate gives an error.
- If I give fewer values, the other variables keep their default value (salary stays 0).
- When I make an object, the number and type of values decides which constructor runs.

**Encapsulation**
- Keep the variable and the methods that work on it in the same class.
- Make the variable `private`, so nobody outside can touch it.
- Add a public getter (`getName`) to read it and a public setter (`setName`) to change it.
- Start getters with `get` and setters with `set`, so everyone knows what they are.

**Access modifiers (small to big)**
- `private`: same class only. A private constructor means no other class can make an object.
- default (nothing written): same class and same package.
- `protected`: same package, plus other packages through inheritance (`extends`).
- `public`: everywhere in the project.
- They work on classes, variables, methods and constructors.

**Collections**
- A collection stores many values in one variable, so I do not need one variable for each value.
- It has methods like `add`, `remove` and `get`.
- The size grows by itself. When it is full, Java makes a bigger one and copies the values over.
- It lives in heap memory.
- It stores objects only, not primitives. For numbers I use the wrapper class: `Integer` for `int`, `Double` for `double`, `Boolean` for `boolean`, `Character` for `char`.
- Hierarchy: `Collection` is on top. `List` and `Set` extend it. `Map` is separate. I cannot make an object of an interface, so I use a class:
  - List: `ArrayList` or `LinkedList`
  - Set: `HashSet` or `LinkedHashSet`
  - Map: `HashMap`

**List (ArrayList)**
- Keeps many values in order and allows duplicates.
- Keeps the insertion order. I get the values back in the order I added them.
- Each value has an index, and the first index is 0.
- `ArrayList<String>` is type safe: adding a number is a compile error.
- Common methods: `add`, `get(index)`, `size()`, `remove(index)`, `contains`.
- To read everything: `for (int i = 0; i < names.size(); i++)`.

**HashMap (from the W3Schools reading)**
- Keeps key and value pairs. I use a key to get a value, not an index.
- Methods: `put`, `get`, `remove`, `clear`, `size`.
- A key can only appear once. Putting the same key again replaces the value, and `size()` only counts unique keys.
- `get` with a key that is not there gives `null`.
- Loop with `keySet()` for keys and `values()` for values.
- Other types need wrapper classes, like `HashMap<String, Integer>`.
- `Map<String, String> m = new HashMap<>();` also works. The variable is the interface and the object is a HashMap.
- `var` (Java 10) lets the compiler work out the type, so I do not write it twice.

**When I would use a Map instead of a List**
I use a List when I only have a group of items and the order matters, like a list of product names. I use a Map when I want to look something up by a name, like stock count by product name. A Map finds the answer straight from the key, so I do not have to loop through everything.
