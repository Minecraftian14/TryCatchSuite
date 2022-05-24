package in.mcxiv.tryCatchSuite.interfaces;

import in.mcxiv.tryCatchSuite.Try;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public interface DangerousCollector<T, A, R> {

    Supplier<A> supplier() throws Exception;

    BiConsumer<A, T> accumulator() throws Exception;

    BinaryOperator<A> combiner() throws Exception;

    Function<A, R> finisher() throws Exception;

    Set<Collector.Characteristics> characteristics() throws Exception;

    default Collector<T, A, R> defaultWrap() {
        return new Collector<T, A, R>() {
            @Override
            public Supplier<A> supplier() {
                return Try.getAnd(DangerousCollector.this::supplier).elseNull();
            }

            @Override
            public BiConsumer<A, T> accumulator() {
                return Try.getAnd(DangerousCollector.this::accumulator).elseNull();
            }

            @Override
            public BinaryOperator<A> combiner() {
                return Try.getAnd(DangerousCollector.this::combiner).elseNull();
            }

            @Override
            public Function<A, R> finisher() {
                return Try.getAnd(DangerousCollector.this::finisher).elseNull();
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Try.getAnd(DangerousCollector.this::characteristics).elseNull();
            }
        };
    }

    default Collector<T, A, R> definedWrap(Collector<T, A, R> def) {
        return new Collector<T, A, R>() {
            @Override
            public Supplier<A> supplier() {
                return Try.getAnd(DangerousCollector.this::supplier).elseGet(def::supplier);
            }

            @Override
            public BiConsumer<A, T> accumulator() {
                return Try.getAnd(DangerousCollector.this::accumulator).elseGet(def::accumulator);
            }

            @Override
            public BinaryOperator<A> combiner() {
                return Try.getAnd(DangerousCollector.this::combiner).elseGet(def::combiner);
            }

            @Override
            public Function<A, R> finisher() {
                return Try.getAnd(DangerousCollector.this::finisher).elseGet(def::finisher);
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Try.getAnd(DangerousCollector.this::characteristics).elseGet(def::characteristics);
            }
        };
    }
}
