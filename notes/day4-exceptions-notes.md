# Day 4 notes: exceptions

From the W3Schools Java Exceptions pages (the course has no exceptions lecture). I tried each example in `day4/exceptions/`.

## What an exception is
- An error that happens while the program runs, like bad input or a wrong index.
- When it happens and nothing handles it, Java stops the program and prints an error message. This is called throwing an exception.
- My run of `myNumbers[10]` on an array of 3 gave: `ArrayIndexOutOfBoundsException: Index 10 out of bounds for length 3`. W3Schools shows `: 10` because it is an older Java version.

## try and catch
- `try` holds the code that might fail.
- `catch` holds the code that runs only if the try block throws an error.
- They always come as a pair.
- With catch the program does not crash. It runs the catch code and keeps going.
- `catch (Exception e)` catches any exception. I can name the exact type, like `catch (NumberFormatException e)`, to catch only that one.
- `e.getMessage()` gives the message text.

## finally
- Runs after try and catch, whatever the result.
- It runs when there is an error and also when there is no error (I tested both).
- It is good for clean up work that must always happen.

## throw
- `throw` makes my own exception: `throw new ArithmeticException("Access denied...")`.
- It goes with an exception type. The text inside the brackets is the message.
- If nothing catches it, the program stops with a stack trace. If I call the method inside try / catch, I can catch it and read the message.
- Do not mix up `throw` (I make the exception) and `catch` (I handle it).

## Common exception types (table)
- `ArithmeticException`: a calculation goes wrong, like `10 / 0` (message: `/ by zero`).
- `ArrayIndexOutOfBoundsException`: an array index that does not exist.
- `StringIndexOutOfBoundsException`: a character position that does not exist in a String.
- `NumberFormatException`: a String cannot be turned into a number, like `Integer.parseInt("abc")`.
- `NullPointerException`: using a variable that is null.
- `FileNotFoundException`, `IOException`, `ClassNotFoundException`, `InputMismatchException`: I read them in the table but did not try them.
- The table lists `ArithmeticError`, but the real class in Java is `ArithmeticException`. I used the exception one.

## parsePort
- `parsePort(String value)` returns the port as an int.
- `Integer.parseInt` throws `NumberFormatException` if the text is not a number. I catch it and throw an `IllegalArgumentException` with a clearer message.
- If the number is below 1 or above 65535, I throw `IllegalArgumentException` with a different message.
- In `main`, I call it inside try / catch and print `e.getMessage()` for each failure.
- A value like `" 443"` (with a space) or `"80.5"` is also rejected, because `parseInt` only accepts plain whole numbers. A `null` is rejected too, with the "must be a number" message.
