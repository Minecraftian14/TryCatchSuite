package in.mcxiv.tryCatchSuite;

import in.mcxiv.tryCatchSuite.interfaces.DangerousFunction;
import in.mcxiv.tryCatchSuite.interfaces.DangerousRunnable;
import in.mcxiv.tryCatchSuite.interfaces.DangerousSupplier;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Try {

    private static BiConsumer<Boolean, Exception> exceptionHandler = (c, e) -> {
        if (c) e.printStackTrace();
    };

    public static BiConsumer<Boolean, Exception> getExceptionHandler() {
        return exceptionHandler;
    }

    public static void setExceptionHandler(BiConsumer<Boolean, Exception> exceptionHandler) {
        Try.exceptionHandler = exceptionHandler;
    }

    private Try() {
    }

    public static void run(DangerousRunnable dangerousRunnable) {
        try {
            dangerousRunnable.run();
        } catch (Exception e) {
            exceptionHandler.accept(true, e);
        }
    }

    public static ExceptionConsumerProvider runAnd(DangerousRunnable dangerousRunnable) {
        return exceptionConsumer -> {
            try {
                dangerousRunnable.run();
            } catch (Exception e) {
                exceptionConsumer.consume(e);
            }
        };
    }

    public static <ReturnType> ReturnType get(DangerousSupplier<ReturnType> dangerousSupplier) {
        try {
            return dangerousSupplier.get();
        } catch (Exception e) {
            exceptionHandler.accept(true, e);
        }
        return null;
    }

    public static <ReturnType> SupplierProvider<ReturnType> getAnd(DangerousSupplier<ReturnType> dangerousSupplier) {
        return returnTypeSupplier -> {
            try {
                return dangerousSupplier.get();
            } catch (Exception e) {
                exceptionHandler.accept(false, e);
                return returnTypeSupplier.get();
            }
        };
    }

    public static <ReturnType> Optional<ReturnType> opt(DangerousSupplier<ReturnType> dangerousSupplier) {
        return Optional.ofNullable(getAnd(dangerousSupplier).elseNull());
    }

    public static <ReturnType> SupplierProviderStep<ReturnType> thatIf(DangerousSupplier<Boolean> condition) {
        return supplier -> returnTypeSupplier -> {
            try {
                if (condition.get()) return supplier.get();
            } catch (Exception e) {
                exceptionHandler.accept(false, e);
            }
            return returnTypeSupplier.get();
        };
    }

    public static <T> T justThrow(Supplier<RuntimeException> supplier) {
        throw supplier.get();
    }

    public static <T, R> Stream<R> map(Stream<T> stream, DangerousFunction<? super T, ? extends R> mapper) {
        return stream.map(t -> {
            try {
                return mapper.apply(t);
            } catch (Exception e) {
                exceptionHandler.accept(false, e);
            }
            return null;
        });
    }

    public static <T, R> Function<? super T, ? extends R> exceptToNull(DangerousFunction<? super T, ? extends R> mapper) {
        return t -> getAnd(() -> mapper.apply(t)).elseNull();
    }

    public static <T, R> Function<? super T, ? extends R> exceptTo(DangerousFunction<? super T, ? extends R> mapper, Supplier<? extends R> def) {
        return t -> {
            R r = getAnd(() -> mapper.apply(t)).elseNull();
            if (Objects.isNull(r)) return def.get();
            return r;
        };
    }

    public static <T> TryStream<T> streaming(Collection<T> collection) {
        return TryStream.of(collection.stream());
    }
}
