# Try Catch Suite

A small set of static functions to drop the `try {} catch {alternative}` block of code within a single line.

It also gives better readability (just like how streams do).

[![](https://img.shields.io/discord/872811194170347520?color=%237289da&logoColor=%23424549)](https://discord.gg/Ar6Zuj2m82)

---

To run some task without caring if it was successful or not.

```
Try.run(() -> some code here which may produce a exception.)

// example
Try.run(() -> System.out.println(1114 / 0));
```

To run a task which may throw an exception, and handle the exception if it fails.

```
Try
    .runAnd(() -> System.out.println(1114 / 0))
    .catchThis(exception -> exception.printStackTrace());
```

To get results from a task which may throw an exception.
(By default the method returns `null` in case the task fails.)

```
int value = Try.get(() -> 1114 / 0);
```

To run a task which may give some results or might throw an exception with a backup plan to give a default value if it
fails.

```
value = Try
    .getAnd(() -> 1114 / 0)
    .elseGive(() -> -1);
    
// or to simply quit
value = Try
    .getAnd(() -> 1114 / 0)
    .elseThrow();
```

To get an Optional of the possible null value.

```
Optional<Integer> opt = Try.opt(() -> 1114 / 0);
```

To start a task according a condition supplier which may throw an exception (defaulting that case to false).

```
Try
    .thatIf(() -> true)
    .thenRun(() -> System.out.println("Hello World!"))
    .elseRun(() -> System.out.println("Bye bye...."));
    
// or to get results
String message = Try
    .<String>thatIf(() -> true)
    .thenGet(() -> "Hello World!")
    .elseGet(() -> "Bye bye....");
```

---

There'a method to have an inline effect for throwing errors too!
This is especially useful when creating lambda expressions inline.

```
Try.justThrow(RuntimeException::new)

// example
/*some stream code*/.filter(this::isNotAnAcceptedValue)
    .forEach(value -> Try.justThrow(RuntimeException::new));
```

Some other code examples which are still in need of a better idea for an easier usage.

```
// To use map in a stream 
IntStream.range(-3, 4)
    .mapToObj(String::valueOf)
    .map(Try.<String, Character>exceptToNull(a -> a.charAt(1)))
    .filter(Objects::nonNull)
    .count()

// But having to <String, Character> spoils the fun of using this tool...
// An alternative way would be
IntStream.range(-3, 4)
    .mapToObj(String::valueOf)
    .map(exceptToNull((String a) -> a.charAt(1)))
    .filter(Objects::nonNull)
    .count()
    
// Similarly it's defaulting alternative
IntStream.range(-3, 4)
    .mapToObj(String::valueOf)
    .map(exceptTo((String a) -> a.charAt(1), () -> '\0'))
    .filter(Objects::nonNull)
    .count()
```