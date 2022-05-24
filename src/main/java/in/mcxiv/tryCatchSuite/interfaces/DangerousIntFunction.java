package in.mcxiv.tryCatchSuite.interfaces;

import java.util.function.IntFunction;
import java.util.function.Supplier;

@FunctionalInterface
public interface DangerousIntFunction<ReturnType> {

    ReturnType apply(int a) throws Exception;

    default IntFunction<ReturnType> defaultWrap() {
        return definedWrap(null);
    }

    default IntFunction<ReturnType> definedWrap(Supplier<? extends ReturnType> def) {
        return argumentType -> {
            try {
                return apply(argumentType);
            } catch (Exception e) {
                return def.get();
            }
        };
    }

}
