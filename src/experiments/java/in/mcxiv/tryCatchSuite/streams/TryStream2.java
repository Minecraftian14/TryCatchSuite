package in.mcxiv.tryCatchSuite.streams;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class TryStream2<Type> implements Stream<Type> {

    public static <T> TryStream2<T> of(Iterable<T> iterable) {
        return of(iterable.iterator());
    }

    public static <T> TryStream2<T> of(Iterator<T> iterator) {
        return new TryStream2<>(iterator);
    }

    private final Iterator<Type> iterator;

    public TryStream2(Iterator<Type> iterable) {
        this.iterator = iterable;
    }

    @Override
    public TryStream2<Type> filter(final Predicate<? super Type> predicate) {
        class FilterJoint implements Iterator<Type> {

            Type next = null;

            @Override
            public boolean hasNext() {
                while (iterator.hasNext() && (!predicate.test(next = iterator.next()))) ;
                return next != null;
            }

            @Override
            public Type next() {
                Type next = this.next;
                this.next = null;
                return next;
            }
        }
        return new TryStream2<>(new FilterJoint());
    }

    @Override
    public <ThatType> TryStream2<ThatType> map(Function<? super Type, ? extends ThatType> mapper) {
        class MapJoint implements Iterator<ThatType> {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public ThatType next() {
                return mapper.apply(iterator.next());
            }
        }
        return new TryStream2<>(new MapJoint());
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super Type> mapper) {
        return IntStream.iterate(0, i -> iterator.hasNext(), i -> mapper.applyAsInt(iterator.next()));
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super Type> mapper) {
        return LongStream.iterate(0, i -> iterator.hasNext(), i -> mapper.applyAsLong(iterator.next()));
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super Type> mapper) {
        return DoubleStream.iterate(0, i -> iterator.hasNext(), i -> mapper.applyAsDouble(iterator.next()));
    }

    @SafeVarargs
    public static <T> TryStream2<T> concatenate(Iterator<T>... iterators) {
        class ConcatenateJoint implements Iterator<T> {

            int index = 0;
            Iterator<T> currentIterator = iterators[0];

            @Override
            public boolean hasNext() {
                boolean hasNext = currentIterator.hasNext();
                if (hasNext) return true;
                while (++index < iterators.length) {
                    currentIterator = iterators[index];
                    if (currentIterator.hasNext()) return true;
                }
                return false;
            }

            @Override
            public T next() {
                return currentIterator.next();
            }
        }
        return new TryStream2<>(new ConcatenateJoint());
    }

    static class ConcatenateJoint_StreamsToTryStream<T> implements Iterator<T> {

        private final Stream<T>[] streams;
        int index = 0;
        Iterator<T> currentIterator;

        public ConcatenateJoint_StreamsToTryStream(Stream<T>[] streams) {
            this.streams = streams;
            currentIterator = streams[0].iterator();
        }

        @Override
        public boolean hasNext() {
            boolean hasNext = currentIterator.hasNext();
            if (hasNext) return true;
            while (++index < streams.length) {
                currentIterator = streams[index].iterator();
                if (currentIterator.hasNext()) return true;
            }
            return false;
        }

        @Override
        public T next() {
            return currentIterator.next();
        }
    }

    @SafeVarargs
    public static <T> TryStream2<T> concatenate(Stream<T>... streams) {
        return new TryStream2<>(new ConcatenateJoint_StreamsToTryStream<>(streams));
    }

    @SafeVarargs
    public static <T> TryStream2<T> concatenate2(TryStream2<T>... streams) {
        return new TryStream2<>(new ConcatenateJoint_StreamsToTryStream<>(streams));
    }

    static class ConcatenateJoint_SupplierToTryStream<T> implements Iterator<T> {

        private final Supplier<? extends Stream<? extends T>> next;
        Iterator<? extends T> currentIterator;

        public ConcatenateJoint_SupplierToTryStream(Supplier<? extends Stream<? extends T>> next) {
            this.next = next;
            currentIterator = next.get().iterator();
        }

        @Override
        public boolean hasNext() {
            if (currentIterator == null) return false;
            boolean hasNext = currentIterator.hasNext();
            if (hasNext) return true;
            Stream<? extends T> stream;
            while ((stream = next.get()) != null)
                currentIterator = stream.iterator();
            if (currentIterator.hasNext()) return true;
            return false;
        }

        @Override
        public T next() {
            return currentIterator.next();
        }
    }

    public static <T> TryStream2<T> concatenate(Supplier<? extends Stream<? extends T>> next) {
        return new TryStream2<>(new ConcatenateJoint_SupplierToTryStream<>(next));
    }

    public static <T> TryStream2<T> concatenate2(Supplier<TryStream2<? extends T>> next) {
        return new TryStream2<>(new ConcatenateJoint_SupplierToTryStream<>(next));
    }

    public static <T> TryStream2<T> concatenateFromIterator(Iterator<? extends Stream<? extends T>> next) {
        return concatenate(() -> next.hasNext() ? next.next() : null);
    }

    @Deprecated
    public static <T> TryStream2<T> concatenateFromIterator2(Iterator<TryStream2<? extends T>> next) {
        return concatenate2(() -> next.hasNext() ? next.next() : null);
    }

    @Override
    public <ThatType> TryStream2<ThatType> flatMap(Function<? super Type, ? extends Stream<? extends ThatType>> mapper) {
        return concatenateFromIterator(map(mapper).iterator());
    }

    @Override
    public IntStream flatMapToInt(Function<? super Type, ? extends IntStream> mapper) {
        var iterator = map(mapper).iterator();
        class FlatMapToIntJoint {
            PrimitiveIterator.OfInt currentIterator = iterator.next().iterator();

            public boolean hasNext() {
                boolean hasNext = currentIterator.hasNext();
                if (hasNext) return true;
                if (!iterator.hasNext()) return false;
                IntStream next;
                while ((next = iterator.next()) != null) {
                    currentIterator = next.iterator();
                    if (currentIterator.hasNext()) return true;
                }
                return false;
            }

            public int next() {
                return currentIterator.nextInt();
            }
        }
        FlatMapToIntJoint joint = new FlatMapToIntJoint();
        return IntStream.iterate(0, i -> joint.hasNext(), operand -> joint.next());
    }

    @Override
    public LongStream flatMapToLong(Function<? super Type, ? extends LongStream> mapper) {
        var iterator = map(mapper).iterator();
        class FlatMapToLongJoint {
            PrimitiveIterator.OfLong currentIterator = iterator.next().iterator();

            public boolean hasNext() {
                boolean hasNext = currentIterator.hasNext();
                if (hasNext) return true;
                if (!iterator.hasNext()) return false;
                LongStream next;
                while ((next = iterator.next()) != null) {
                    currentIterator = next.iterator();
                    if (currentIterator.hasNext()) return true;
                }
                return false;
            }

            public long next() {
                return currentIterator.nextLong();
            }
        }
        FlatMapToLongJoint joint = new FlatMapToLongJoint();
        return LongStream.iterate(0, i -> joint.hasNext(), operand -> joint.next());
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super Type, ? extends DoubleStream> mapper) {
        var iterator = map(mapper).iterator();
        class FlatMapToDoubleJoint {
            PrimitiveIterator.OfDouble currentIterator = iterator.next().iterator();

            public boolean hasNext() {
                boolean hasNext = currentIterator.hasNext();
                if (hasNext) return true;
                if (!iterator.hasNext()) return false;
                DoubleStream next;
                while ((next = iterator.next()) != null) {
                    currentIterator = next.iterator();
                    if (currentIterator.hasNext()) return true;
                }
                return false;
            }

            public double next() {
                return currentIterator.nextDouble();
            }
        }
        FlatMapToDoubleJoint joint = new FlatMapToDoubleJoint();
        return DoubleStream.iterate(0, i -> joint.hasNext(), operand -> joint.next());
    }

    @Override
    public TryStream2<Type> distinct() {
        class DistinctJoint implements Predicate<Type> {
            final LinkedList<Integer> list = new LinkedList<>();

            @Override
            public boolean test(Type type) {
                int code = type.hashCode();
                if (list.contains(code)) return false;
                list.add(code);
                return true;
            }
        }
        return filter(new DistinctJoint());
    }

    @Override
    public TryStream2<Type> sorted() {
        @SuppressWarnings("unchecked")
        Comparator<? super Type> comp = (Comparator<? super Type>) Comparator.naturalOrder();
        return sorted(comp);
    }

    @Override
    public TryStream2<Type> sorted(Comparator<? super Type> comparator) {
        final ArrayList<Type> list = new ArrayList<>();
        while (iterator.hasNext()) list.add(iterator.next());
        list.sort(comparator);
        return new TryStream2<>(list.iterator());
    }

    @Override
    public TryStream2<Type> peek(Consumer<? super Type> action) {
        class PeekJoint implements Iterator<Type> {

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Type next() {
                Type next = iterator.next();
                action.accept(next);
                return next;
            }
        }
        return new TryStream2<>(new PeekJoint());
    }

    @Override
    public TryStream2<Type> limit(long maxSize) {
        class LimitJoint implements Iterator<Type> {
            long index = 0;

            @Override
            public boolean hasNext() {
                return index < maxSize && iterator.hasNext();
            }

            @Override
            public Type next() {
                index++;
                return iterator.next();
            }
        }
        return new TryStream2<>(new LimitJoint());
    }

    @Override
    public TryStream2<Type> skip(long n) {
        for (long index = 0; index < n && iterator.hasNext(); index++) iterator.next();
        return this;
    }

    @Override
    public void forEach(Consumer<? super Type> action) {
        while (iterator.hasNext()) action.accept(iterator.next());
    }

    @Override
    public void forEachOrdered(Consumer<? super Type> action) {
        // TODO: right?
        sorted().forEach(action);
    }

    @Override
    public Object[] toArray() {
        ArrayList<Object> list = new ArrayList<>();
        while (iterator.hasNext()) list.add(iterator.next());
        return list.toArray();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        // TODO:
        return null;
    }

    @Override
    public Type reduce(Type identity, BinaryOperator<Type> accumulator) {
        while (iterator.hasNext())
            identity = accumulator.apply(identity, iterator.next());
        return identity;
    }

    @Override
    public Optional<Type> reduce(BinaryOperator<Type> accumulator) {
        return Optional.ofNullable(reduce(null, accumulator));
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super Type, U> accumulator, BinaryOperator<U> combiner) {
        while (iterator.hasNext())
            identity = accumulator.apply(identity, iterator.next());
        return identity;
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super Type> accumulator, BiConsumer<R, R> combiner) {
        return null;
    }

    @Override
    public <R, A> R collect(Collector<? super Type, A, R> collector) {
        return null;
    }

    @Override
    public Optional<Type> min(Comparator<? super Type> comparator) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> max(Comparator<? super Type> comparator) {
        return Optional.empty();
    }

    @Override
    public long count() {
        long count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }

    @Override
    public boolean anyMatch(Predicate<? super Type> predicate) {
        return false;
    }

    @Override
    public boolean allMatch(Predicate<? super Type> predicate) {
        return false;
    }

    @Override
    public boolean noneMatch(Predicate<? super Type> predicate) {
        return false;
    }

    @Override
    public Optional<Type> findFirst() {
        return Optional.empty();
    }

    @Override
    public Optional<Type> findAny() {
        return Optional.empty();
    }

    @Override
    public Iterator<Type> iterator() {
        // TODO: is it right?
        return iterator;
    }

    @Override
    public Spliterator<Type> spliterator() {
        return null;
    }

    @Override
    public boolean isParallel() {
        return false;
    }

    @Override
    public Stream<Type> sequential() {
        return null;
    }

    @Override
    public Stream<Type> parallel() {
        return null;
    }

    @Override
    public Stream<Type> unordered() {
        return null;
    }

    @Override
    public Stream<Type> onClose(Runnable closeHandler) {
        return null;
    }

    @Override
    public void close() {

    }
}
