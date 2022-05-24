package in.mcxiv.tryCatchSuite.interfaces;

import java.util.function.ToIntFunction;

public interface DangerousToIntFunction<ArgumentType> {

    int applyAsInt(ArgumentType value) throws Exception;

    default ToIntFunction<ArgumentType> defaultWrap() {
        return definedWrap(-1);
    }

    default ToIntFunction<ArgumentType> definedWrap(int def) {
        return value -> {
            try {
                return applyAsInt(value);
            } catch (Exception e) {
                return def;
            }
        };
    }
}
