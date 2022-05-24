package in.mcxiv.tryCatchSuite.interfaces;

import in.mcxiv.tryCatchSuite.Try;

import java.util.function.Function;
import java.util.function.Supplier;

@FunctionalInterface
public interface DangerousFunction<ArgumentType, ReturnType> {

    ReturnType apply(ArgumentType a) throws Exception;

    default Function<ArgumentType, ReturnType> defaultWrap() {
        return argumentType -> Try.getAnd(() -> apply(argumentType)).elseNull();
    }

    default Function<ArgumentType, ReturnType> definedWrap(Supplier<? extends ReturnType> def) {
        return argumentType -> Try.getAnd(() -> apply(argumentType)).elseGet(def);
    }

    @SuppressWarnings("unchecked")
    default <R> Function<? super ArgumentType, ? extends ReturnType> definedWrapT(Supplier<? extends R> def) {
        return argumentType -> {
            ReturnType r = defaultWrap().apply(argumentType);
            return r != null ? r : (ReturnType) def.get();
        };
    }
}
