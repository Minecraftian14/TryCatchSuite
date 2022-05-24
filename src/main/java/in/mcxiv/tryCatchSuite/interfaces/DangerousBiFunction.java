package in.mcxiv.tryCatchSuite.interfaces;

import in.mcxiv.tryCatchSuite.Try;

import java.util.function.BiFunction;
import java.util.function.Supplier;

@FunctionalInterface
public interface DangerousBiFunction<Argument1Type, Argument2Type, ReturnType> {
    ReturnType apply(Argument1Type t, Argument2Type u) throws Exception;

    default BiFunction<Argument1Type, Argument2Type, ReturnType> defaultWrap() {
        return (t, u) -> Try.getAnd(() -> apply(t, u)).elseNull();
    }

    default BiFunction<Argument1Type, Argument2Type, ReturnType> definedWrap(Supplier<ReturnType> def) {
        return (t, u) -> Try.getAnd(() -> apply(t, u)).elseGet(def);
    }

}
