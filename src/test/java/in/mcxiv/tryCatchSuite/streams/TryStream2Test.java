package in.mcxiv.tryCatchSuite.streams;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static in.mcxiv.tryCatchSuite.TryTest.alsoPrtln;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TryStream2Test {

    @Test
    void count() {
        assertEquals(100, alsoPrtln(getAStream().count()));
    }

    @Test
    void filter() {
        assertTrue(alsoPrtln(
                getAStream().filter(s -> s.length() > 5).count()
        ) > 25);
    }

    @Test
    void map() {
        assertEquals(100, (long) alsoPrtln(
                getAStream().map(String::length).count()
        ));
        assertTrue(alsoPrtln(
                getAStream().map(String::length).filter(integer -> integer > 5).count()
        ) > 25);
    }

    @Test
    void mapToInt_mapToLong_mapToDouble() {
        assertTrue(alsoPrtln(
                getAStream().mapToInt(String::length).filter(integer -> integer > 5).count()
        ) > 25);
        assertTrue(alsoPrtln(
                getAStream().mapToLong(String::length).filter(integer -> integer > 5).count()
        ) > 25);
        assertTrue(alsoPrtln(
                getAStream().mapToDouble(String::length).filter(integer -> integer > 5).count()
        ) > 25);
    }

    @Test
    void concatenate() {
        assertEquals(200, alsoPrtln(TryStream2.concatenate(getAStream().iterator(), getAStream().iterator()).count()));
        assertEquals(200, alsoPrtln(TryStream2.concatenate(getAStream(), getAStream()).count()));
        assertEquals(200, alsoPrtln(TryStream2.concatenate2(getAStream(), getAStream()).count()));

        var streams1 = List.of(getAStream(), getAStream()).iterator();
        assertEquals(200, alsoPrtln(TryStream2.concatenate(() -> streams1.hasNext() ? streams1.next() : null).count()));
        var streams2 = List.of(getAStream(), getAStream()).iterator();
        assertEquals(200, alsoPrtln(TryStream2.concatenate2(() -> streams2.hasNext() ? streams2.next() : null).count()));

        var streams3 = List.of(getAStream(), getAStream()).iterator();
        assertEquals(200, alsoPrtln(TryStream2.concatenateFromIterator(streams3).count()));
//        var streams4 = List.of(getAStream(), getAStream()).iterator();
//        assertEquals(200, alsoPrtln(TryStream2.<CharSequence>concatenateFromIterator2(streams4).count()));
    }

    @Test
    void flatMap() {
        assertEquals(2, alsoPrtln(TryStream2.of(List.of(getAStream(), getAStream())).count()));
        assertEquals(200, alsoPrtln(
                TryStream2.of(List.of(getAStream(), getAStream()))
                        .flatMap(stream -> stream)
                        .count()
        ));
    }

    @Test
    void flatMapToInt_flatMapToLong_flatMapToDouble() {
        assertEquals(200, alsoPrtln(
                TryStream2.of(List.of(getAStream(), getAStream()))
                        .flatMapToInt(stream -> stream.mapToInt(String::length))
                        .count()
        ));
        assertEquals(200, alsoPrtln(
                TryStream2.of(List.of(getAStream(), getAStream()))
                        .flatMapToLong(stream -> stream.mapToLong(String::length))
                        .count()
        ));
        assertEquals(200, alsoPrtln(
                TryStream2.of(List.of(getAStream(), getAStream()))
                        .flatMapToDouble(stream -> stream.mapToDouble(String::length))
                        .count()
        ));
    }

    TryStream2<String> getAStream() {
        return new TryStream2<>(new Random().ints(100, 0, 10).mapToObj("+"::repeat).toList().iterator());
    }
}