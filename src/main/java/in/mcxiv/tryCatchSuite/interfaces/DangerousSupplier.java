package in.mcxiv.tryCatchSuite.interfaces;

import in.mcxiv.tryCatchSuite.Try;

import java.util.function.Supplier;

@FunctionalInterface
public interface DangerousSupplier<ReturnType> {
    ReturnType get() throws Exception;

    default Supplier<ReturnType> defaultWrap() {
        return () -> Try.getAnd(this::get).elseNull();
    }

    default Supplier<ReturnType> definedWrap(Supplier<ReturnType> def) {
        return () -> Try.getAnd(this::get).elseGet(def);
    }

}
