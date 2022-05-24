package in.mcxiv.tryCatchSuite.interfaces;

import in.mcxiv.tryCatchSuite.Try;

import java.util.function.Consumer;

@FunctionalInterface
public interface DangerousConsumer<ArgumentType> {

    void accept(ArgumentType a) throws Exception;

    default Consumer<ArgumentType> defaultWrap() {
        return argumentType -> Try.runAnd(() -> accept(argumentType)).doNothing();
    }

    default Consumer<ArgumentType> definedWrap(Consumer<ArgumentType> def) {
        return argumentType -> Try.runAnd(() -> accept(argumentType)).catchThis(exception -> def.accept(argumentType));
    }
}
