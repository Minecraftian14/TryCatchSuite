package in.mcxiv.tryCatchSuite;

import in.mcxiv.tryCatchSuite.interfaces.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class TryStream<Type> implements Stream<Type> {

    Stream<Type> stream;

    public static <T> TryStream<T> of(Stream<T> stream) {
        return new TryStream<>(stream);
    }

    public TryStream(Stream<Type> stream) {
        this.stream = stream;
    }

    /**
     * @see Stream#filter(Predicate)
     */
    public TryStream<Type> filter2(DangerousPredicate<? super Type> predicate) {
        stream = stream.filter(predicate.defaultWrap());
        return this;
    }

    /**
     * @param def the default value returned by the predicated in case it throws an error.
     * @see Stream#filter(Predicate)
     */
    public TryStream<Type> filter2(DangerousPredicate<? super Type> predicate, boolean def) {
        stream = stream.filter(predicate.definedWrap(def));
        return this;
    }

    @Override
    public Stream<Type> filter(Predicate<? super Type> predicate) {
        stream = stream.filter(predicate);
        return this;
    }

    /**
     * @see Stream#map(Function)
     */
    public <R> TryStream<R> map2(DangerousFunction<? super Type, ? extends R> mapper) {
        return of(stream.map(mapper.defaultWrap()));
    }

    /**
     * @param def the default value returned by the mapper in case it throws an error.
     * @see Stream#map(Function)
     */
    @Deprecated
    public <R> TryStream<R> map2(DangerousFunction<? super Type, ? extends R> mapper, Supplier<? extends R> def) {
        return of(stream.map(mapper.definedWrapT(def)));
    }

    @Override
    public <R> TryStream<R> map(Function<? super Type, ? extends R> mapper) {
        return of(stream.map(mapper));
    }

    /**
     * @see Stream#mapToInt(ToIntFunction)
     */
    public IntStream mapToInt2(DangerousToIntFunction<? super Type> mapper) {
        return stream.mapToInt(mapper.defaultWrap());
    }

    /**
     * @param def the default value returned by the function in case it throws an error.
     * @see Stream#mapToInt(ToIntFunction)
     */
    public IntStream mapToInt2(DangerousToIntFunction<? super Type> mapper, int def) {
        return stream.mapToInt(mapper.definedWrap(def));
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super Type> mapper) {
        return stream.mapToInt(mapper);
    }

    /**
     * @see Stream#mapToLong(ToLongFunction)
     */
    public LongStream mapToLong2(DangerousToLongFunction<? super Type> mapper) {
        return stream.mapToLong(mapper.defaultWrap());
    }

    /**
     * @param def the default value returned by the function in case it throws an error.
     * @see Stream#mapToLong(ToLongFunction)
     */
    public LongStream mapToLong2(DangerousToLongFunction<? super Type> mapper, long def) {
        return stream.mapToLong(mapper.definedWrap(def));
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super Type> mapper) {
        return stream.mapToLong(mapper);
    }

    /**
     * @see Stream#mapToDouble(ToDoubleFunction)
     */
    public DoubleStream mapToInt2(DangerousToDoubleFunction<? super Type> mapper) {
        return stream.mapToDouble(mapper.defaultWrap());
    }

    /**
     * @param def the default value returned by the function in case it throws an error.
     * @see Stream#mapToDouble(ToDoubleFunction)
     */
    public DoubleStream mapToInt2(DangerousToDoubleFunction<? super Type> mapper, double def) {
        return stream.mapToDouble(mapper.definedWrap(def));
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super Type> mapper) {
        return stream.mapToDouble(mapper);
    }

    /**
     * @see Stream#flatMap(Function)
     */
    public <R> TryStream<R> flatMap2(DangerousFunction<? super Type, ? extends Stream<? extends R>> mapper) {
        return of(stream.flatMap(mapper.defaultWrap()));
    }

    @Override
    public <R> TryStream<R> flatMap(Function<? super Type, ? extends Stream<? extends R>> mapper) {
        return of(stream.flatMap(mapper));
    }

    public IntStream flatMapToInt2(DangerousFunction<? super Type, ? extends IntStream> mapper) {
        return stream.flatMapToInt(mapper.defaultWrap());
    }

    @Override
    public IntStream flatMapToInt(Function<? super Type, ? extends IntStream> mapper) {
        return stream.flatMapToInt(mapper);
    }

    public LongStream flatMapToLong2(DangerousFunction<? super Type, ? extends LongStream> mapper) {
        return stream.flatMapToLong(mapper.defaultWrap());
    }

    @Override
    public LongStream flatMapToLong(Function<? super Type, ? extends LongStream> mapper) {
        return stream.flatMapToLong(mapper);
    }

    public DoubleStream flatMapToDouble2(DangerousFunction<? super Type, ? extends DoubleStream> mapper) {
        return stream.flatMapToDouble(mapper.defaultWrap());
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super Type, ? extends DoubleStream> mapper) {
        return stream.flatMapToDouble(mapper);
    }

    /**
     * @see Stream#distinct()
     */
    @Override
    public TryStream<Type> distinct() {
        stream = stream.distinct();
        return this;
    }

    /**
     * @see Stream#sorted()
     */
    @Override
    public TryStream<Type> sorted() {
        stream = stream.sorted();
        return this;
    }

    /**
     * @see Stream#sorted(Comparator)
     */
    public TryStream<Type> sorted2(DangerousComparator<? super Type> comparator) {
        stream = stream.sorted(comparator.defaultWrap());
        return this;
    }

    @Override
    public TryStream<Type> sorted(Comparator<? super Type> comparator) {
        stream = stream.sorted(comparator);
        return this;
    }

    /**
     * @see Stream#peek(Consumer)
     */
    public TryStream<Type> peek2(DangerousConsumer<? super Type> action) {
        stream = stream.peek(action.defaultWrap());
        return this;
    }

    @Override
    public TryStream<Type> peek(Consumer<? super Type> action) {
        stream = stream.peek(action);
        return this;
    }

    /**
     * @see Stream#limit(long)
     */
    @Override
    public TryStream<Type> limit(long maxSize) {
        stream = stream.limit(maxSize);
        return this;
    }

    /**
     * @see Stream#skip(long)
     */
    @Override
    public TryStream<Type> skip(long n) {
        stream = stream.skip(n);
        return this;
    }

    /**
     * @see Stream#forEach(Consumer)
     */
    public void forEach2(DangerousConsumer<? super Type> action) {
        stream.forEach(action.defaultWrap());
    }

    @Override
    public void forEach(Consumer<? super Type> action) {
        stream.forEach(action);
    }

    /**
     * @see Stream#forEachOrdered(Consumer)
     */
    public void forEachOrdered2(DangerousConsumer<? super Type> action) {
        stream.forEachOrdered(action.defaultWrap());
    }

    @Override
    public void forEachOrdered(Consumer<? super Type> action) {
        stream.forEachOrdered(action);
    }

    /**
     * @see Stream#toArray()
     */
    @Override
    public Object[] toArray() {
        return stream.toArray();
    }

    /**
     * @see Stream#toArray(IntFunction)
     */
    public <A> A[] toArray2(DangerousIntFunction<A[]> generator) {
        IntFunction<A[]> intFunction = generator.defaultWrap();
        //noinspection SuspiciousToArrayCall
        return stream.<A>toArray(intFunction);
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        //noinspection SuspiciousToArrayCall
        return stream.toArray(generator);
    }

    /**
     * @see Stream#reduce(Object, BinaryOperator)
     */
    public Type reduce2(Type identity, DangerousBinaryOperator<Type> accumulator) {
        return stream.reduce(identity, accumulator.defaultWrap());
    }

    /**
     * @see Stream#reduce(BinaryOperator)
     */
    public Optional<Type> reduce2(DangerousBinaryOperator<Type> accumulator) {
        return stream.reduce(accumulator.defaultWrap());
    }

    /**
     * @see Stream#reduce(Object, BiFunction, BinaryOperator)
     */
    public <U> U reduce2(U identity, DangerousBiFunction<U, ? super Type, U> accumulator, DangerousBinaryOperator<U> combiner) {
        return stream.reduce(identity, accumulator.defaultWrap(), combiner.defaultWrap());
    }

    @Override
    public Type reduce(Type identity, BinaryOperator<Type> accumulator) {
        return stream.reduce(identity, accumulator);
    }

    @Override
    public Optional<Type> reduce(BinaryOperator<Type> accumulator) {
        return stream.reduce(accumulator);
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super Type, U> accumulator, BinaryOperator<U> combiner) {
        return stream.reduce(identity, accumulator, combiner);
    }

    /**
     * @see Stream#collect(Supplier, BiConsumer, BiConsumer)
     */
    public <R> R collect2(DangerousSupplier<R> supplier, DangerousBiConsumer<R, ? super Type> accumulator, DangerousBiConsumer<R, R> combiner) {
        return stream.collect(supplier.defaultWrap(), accumulator.defaultWrap(), combiner.defaultWrap());
    }

    /**
     * @see Stream#collect(Collector)
     */
    public <R, A> R collect2(DangerousCollector<? super Type, A, R> collector) {
        return stream.collect(collector.defaultWrap());
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super Type> accumulator, BiConsumer<R, R> combiner) {
        return stream.collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<? super Type, A, R> collector) {
        return stream.collect(collector);
    }

    /**
     * @see Stream#min(Comparator)
     */
    public Optional<Type> min2(DangerousComparator<? super Type> comparator) {
        return stream.min(comparator.defaultWrap());
    }

    @Override
    public Optional<Type> min(Comparator<? super Type> comparator) {
        return stream.min(comparator);
    }

    /**
     * @see Stream#max(Comparator)
     */
    public Optional<Type> max2(DangerousComparator<? super Type> comparator) {
        return stream.max(comparator.defaultWrap());
    }

    @Override
    public Optional<Type> max(Comparator<? super Type> comparator) {
        return stream.max(comparator);
    }

    /**
     * @see Stream#count()
     */
    @Override
    public long count() {
        return stream.count();
    }

    /**
     * @see Stream#anyMatch(Predicate)
     */
    public boolean anyMatch2(DangerousPredicate<? super Type> predicate) {
        return stream.anyMatch(predicate.defaultWrap());
    }

    @Override
    public boolean anyMatch(Predicate<? super Type> predicate) {
        return stream.anyMatch(predicate);
    }

    /**
     * @see Stream#allMatch(Predicate)
     */
    public boolean allMatch2(DangerousPredicate<? super Type> predicate) {
        return stream.allMatch(predicate.defaultWrap());
    }

    @Override
    public boolean allMatch(Predicate<? super Type> predicate) {
        return stream.allMatch(predicate);
    }

    /**
     * @see Stream#noneMatch(Predicate)
     */
    public boolean noneMatch2(DangerousPredicate<? super Type> predicate) {
        return stream.noneMatch(predicate.defaultWrap());
    }

    @Override
    public boolean noneMatch(Predicate<? super Type> predicate) {
        return stream.noneMatch(predicate);
    }

    /**
     * @see Stream#findFirst()
     */
    @Override
    public Optional<Type> findFirst() {
        return stream.findFirst();
    }

    /**
     * @see Stream#findAny()
     */
    @Override
    public Optional<Type> findAny() {
        return stream.findAny();
    }

    /**
     * @see Stream#iterator()
     */
    @Override
    public Iterator<Type> iterator() {
        return stream.iterator();
    }

    /**
     * @see Stream#spliterator()
     */
    @Override
    public Spliterator<Type> spliterator() {
        return stream.spliterator();
    }

    /**
     * @see Stream#isParallel()
     */
    @Override
    public boolean isParallel() {
        return stream.isParallel();
    }

    /**
     * @see Stream#sequential()
     */
    @Override
    public TryStream<Type> sequential() {
        stream = stream.sequential();
        return this;
    }

    /**
     * @see Stream#parallel()
     */
    @Override
    public TryStream<Type> parallel() {
        stream = stream.parallel();
        return this;
    }

    /**
     * @see Stream#unordered()
     */
    @Override
    public TryStream<Type> unordered() {
        stream = stream.unordered();
        return this;
    }

    /**
     * @see Stream#onClose(Runnable)
     */
    public TryStream<Type> onClose2(DangerousRunnable closeHandler) {
        stream = stream.onClose(() -> Try.run(closeHandler));
        return this;
    }

    @Override
    public TryStream<Type> onClose(Runnable closeHandler) {
        stream = stream.onClose(closeHandler);
        return this;
    }

    /**
     * @see Stream#close()
     */
    @Override
    public void close() {
        stream.close();
    }

    public Stream<Type> stream() {
        return stream;
    }

    // <only for java 16 and above>

    public <R> TryStream<R> mapMulti2(DangerousBiConsumer<? super Type, ? super Consumer<R>> mapper) {
        return of(stream.mapMulti(mapper.defaultWrap()));
    }

    @Override
    public <R> TryStream<R> mapMulti(BiConsumer<? super Type, ? super Consumer<R>> mapper) {
        return of(stream.mapMulti(mapper));
    }

    public TryStream<Type> takeWhile2(DangerousPredicate<? super Type> predicate) {
        stream = stream.takeWhile(predicate.defaultWrap());
        return this;
    }

    @Override
    public TryStream<Type> takeWhile(Predicate<? super Type> predicate) {
        stream = stream.takeWhile(predicate);
        return this;
    }

    public TryStream<Type> dropWhile2(DangerousPredicate<? super Type> predicate) {
        stream = stream.dropWhile(predicate.defaultWrap());
        return this;
    }

    @Override
    public TryStream<Type> dropWhile(Predicate<? super Type> predicate) {
        stream = stream.dropWhile(predicate);
        return this;
    }

    public IntStream mapMultiToInt2(DangerousBiConsumer<? super Type, ? super IntConsumer> mapper) {
        return stream.mapMultiToInt(mapper.defaultWrap());
    }

    @Override
    public IntStream mapMultiToInt(BiConsumer<? super Type, ? super IntConsumer> mapper) {
        return stream.mapMultiToInt(mapper);
    }

    public LongStream mapMultiToLong2(DangerousBiConsumer<? super Type, ? super LongConsumer> mapper) {
        return stream.mapMultiToLong(mapper.defaultWrap());
    }

    @Override
    public LongStream mapMultiToLong(BiConsumer<? super Type, ? super LongConsumer> mapper) {
        return stream.mapMultiToLong(mapper);
    }

    public DoubleStream mapMultiToDouble2(DangerousBiConsumer<? super Type, ? super DoubleConsumer> mapper) {
        return stream.mapMultiToDouble(mapper.defaultWrap());
    }

    @Override
    public DoubleStream mapMultiToDouble(BiConsumer<? super Type, ? super DoubleConsumer> mapper) {
        return stream.mapMultiToDouble(mapper);
    }

    @Override
    public List<Type> toList() {
        return stream.toList();
    }

    // </only for java 16 and above>
}
