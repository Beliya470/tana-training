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

**Instance and static variables**
- Instance variable: written inside the class, outside methods. One copy for each object. Gets a default value if I do not set one (null, 0, false).
- Static variable: has the word `static`. One copy for the whole class.

**Constructors**
- Default constructor: no parameters. Java adds it only if I write no constructor at all.
- Parameterized constructor: takes values, so each object can start with different data.
- Once I write my own constructor, Java does not add the default one.

**Access modifiers (small to big)**
- `private`: same class only.
- default (nothing written): same class and same package.
- `protected`: same package, plus other packages through inheritance.
- `public`: everywhere.

**List**
- Keeps many values in one variable, in order.
- Allows duplicates.
- Use `get(index)` to read. The first index is 0.
- Common methods: `add`, `get`, `size`, `remove`, `contains`.

**HashMap**
- Keeps key and value pairs.
- A key can only appear once. Putting the same key again replaces the value.
- `get` with a key that is not there gives `null`.
- Loop with `keySet()`.

**When I would use a Map instead of a List**
I use a List when I only have a group of items and the order matters, like a list of product names. I use a Map when I want to look something up by a name, like stock count by product name. A Map finds the answer straight from the key, so I do not have to loop through everything.
