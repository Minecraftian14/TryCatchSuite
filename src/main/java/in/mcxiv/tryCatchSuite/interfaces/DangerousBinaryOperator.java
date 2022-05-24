package in.mcxiv.tryCatchSuite.interfaces;

import in.mcxiv.tryCatchSuite.Try;

import java.util.function.BinaryOperator;
import java.util.function.Supplier;

@FunctionalInterface
public interface DangerousBinaryOperator<Type> extends DangerousBiFunction<Type, Type, Type> {

    default BinaryOperator<Type> defaultWrap() {
        return (t, u) -> Try.getAnd(() -> apply(t, u)).elseNull();
    }

    default BinaryOperator<Type> definedWrap(Supplier<Type> def) {
        return (t, u) -> Try.getAnd(() -> apply(t, u)).elseGet(def);
    }

}
