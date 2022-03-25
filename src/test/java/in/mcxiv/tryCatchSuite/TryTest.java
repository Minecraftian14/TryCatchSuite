package in.mcxiv.tryCatchSuite;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"NumericOverflow", "Convert2MethodRef", "divzero"})
class TryTest {
    @Test
    void simpleMethodTest() {

        AtomicInteger passes = new AtomicInteger();

        assertDoesNotThrow(() -> Try.run(() -> passes.addAndGet(alsoPrtln(10 / 10))));
        assertEquals(1, passes.get());
        assertDoesNotThrow(() -> Try.run(() -> passes.addAndGet(alsoPrtln(10 / 0))));
        assertEquals(1, passes.get());

        assertDoesNotThrow(() -> Try.runAnd(() -> passes.addAndGet(alsoPrtln(10 / 10))).doNothing());
        assertEquals(2, passes.get());
        assertDoesNotThrow(() -> Try.runAnd(() -> passes.addAndGet(alsoPrtln(10 / 0))).catchThis(e -> passes.addAndGet(alsoPrtln(2))));
        assertEquals(4, passes.get());


        assertEquals(1, Try.get(() -> alsoPrtln(10 / 10)));
        assertNull(Try.get(() -> alsoPrtln(10 / 0)));

        assertEquals(1, Try.getAnd(() -> alsoPrtln(10 / 10)).elseGet(() -> 0));
        assertEquals(0, Try.getAnd(() -> alsoPrtln(10 / 0)).elseGet(() -> 0));
        assertNull(Try.getAnd(() -> alsoPrtln(10 / 0)).elseNull());
        assertThrows(Exception.class, () -> Try.getAnd(() -> alsoPrtln(10 / 0)).elseThrow());


        assertEquals(1, Try.opt(() -> alsoPrtln(10 / 10)).orElse(0));
        assertEquals(0, Try.opt(() -> alsoPrtln(10 / 0)).orElse(0));


        Try.thatIf(() -> true).thenGet(passes::incrementAndGet).elseGet(Assertions::fail);
        assertEquals(5, passes.get());
        Try.thatIf(() -> false).thenGet(() -> 0).elseGet(passes::incrementAndGet);
        assertEquals(6, passes.get());


        assertThrows(RuntimeException.class, () -> Try.justThrow(RuntimeException::new));

        assertEquals(3, alsoPrtln(
                IntStream.range(-3, 4)
                        .mapToObj(String::valueOf)
                        .map(Try.exceptToNull((String a) -> a.charAt(1)))
                        .filter(Objects::nonNull)
                        .count())
        );
        assertEquals(7, alsoPrtln(
                IntStream.range(-3, 4)
                        .mapToObj(String::valueOf)
                        .map(Try.<String, Character>exceptTo(a -> a.charAt(1), () -> '\0'))
                        .filter(Objects::nonNull)
                        .count())
        );
    }

    private <T> T alsoPrtln(T t) {
        System.out.println(">>> " + t.toString());
        return t;
    }

    @Test
    void shortifiedForReadMe() {

        Try.run(() -> System.out.println(1114 / 0));

        Try.runAnd(() -> System.out.println(1114 / 0)).doNothing();
        Try
                .runAnd(() -> System.out.println(1114 / 0))
                .catchThis(exception -> exception.printStackTrace());


        Integer value = Try.get(() -> 1114 / 0);

        value = Try
                .getAnd(() -> 1114 / 0)
                .elseGet(() -> -1);

        value = Try
                .getAnd(() -> 1114 / 0)
                .elseNull();

        value = Try
                .getAnd(() -> 1114 / 0)
                .elseThrow();

        Optional<Integer> opt = Try.opt(() -> 1114 / 0);


        Try
                .thatIf(() -> true)
                .thenRun(() -> System.out.println("Hello World!"))
                .elseRun(() -> System.out.println("Bye bye...."));

        String message = Try
                .<String>thatIf(() -> true)
                .thenGet(() -> "Hello World!")
                .elseGet(() -> "Bye bye....");


        assertEquals(3, alsoPrtln(
                IntStream.range(-3, 4)
                        .mapToObj(String::valueOf)
                        .map(Try.exceptToNull((String a) -> a.charAt(1)))
                        .filter(Objects::nonNull)
                        .count())
        );
        assertEquals(7, alsoPrtln(
                IntStream.range(-3, 4)
                        .mapToObj(String::valueOf)
                        .map(Try.exceptTo((String a) -> a.charAt(1), () -> '\0'))
                        .filter(Objects::nonNull)
                        .count())
        );
    }
}