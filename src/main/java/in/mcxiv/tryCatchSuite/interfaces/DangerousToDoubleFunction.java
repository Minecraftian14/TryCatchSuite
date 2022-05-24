package in.mcxiv.tryCatchSuite.interfaces;


import java.util.function.ToDoubleFunction;

public interface DangerousToDoubleFunction<ArgumentType> {

    int applyAsDouble(ArgumentType value) throws Exception;

    default ToDoubleFunction<ArgumentType> defaultWrap() {
        return definedWrap(-1d);
    }

    default ToDoubleFunction<ArgumentType> definedWrap(double def) {
        return value -> {
            try {
                return applyAsDouble(value);
            } catch (Exception e) {
                return def;
            }
        };
    }
}
