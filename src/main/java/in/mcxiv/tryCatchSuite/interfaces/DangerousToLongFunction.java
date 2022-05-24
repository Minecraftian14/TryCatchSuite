package in.mcxiv.tryCatchSuite.interfaces;

import java.util.function.ToLongFunction;

public interface DangerousToLongFunction<ArgumentType> {

    int applyAsLong(ArgumentType value) throws Exception;

    default ToLongFunction<ArgumentType> defaultWrap() {
        return definedWrap(-1L);
    }

    default ToLongFunction<ArgumentType> definedWrap(long def) {
        return value -> {
            try {
                return applyAsLong(value);
            } catch (Exception e) {
                return def;
            }
        };
    }
}
