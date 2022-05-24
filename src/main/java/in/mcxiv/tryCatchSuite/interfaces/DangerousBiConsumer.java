package in.mcxiv.tryCatchSuite.interfaces;

import in.mcxiv.tryCatchSuite.Try;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface DangerousBiConsumer<Argument1Type, Argument2Type> {

    void accept(Argument1Type t, Argument2Type u);

    default BiConsumer<Argument1Type, Argument2Type> defaultWrap() {
        return (t, u) -> Try.runAnd(() -> accept(t, u)).doNothing();
    }

    default BiConsumer<Argument1Type, Argument2Type> definedWrap(BiConsumer<Argument1Type, Argument2Type> def) {
        return (t, u) -> Try.runAnd(() -> accept(t, u)).catchThis(exception -> def.accept(t, u));
    }
}
