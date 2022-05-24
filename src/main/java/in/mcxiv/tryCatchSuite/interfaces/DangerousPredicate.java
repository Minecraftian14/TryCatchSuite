package in.mcxiv.tryCatchSuite.interfaces;

import java.util.function.Predicate;

@FunctionalInterface
public interface DangerousPredicate<ArgumentType> {

    boolean test(ArgumentType a) throws Exception;

    default Predicate<ArgumentType> defaultWrap() {
        return definedWrap(false);
    }

    default Predicate<ArgumentType> definedWrap(boolean def) {
        return t -> {
            try {
                return test(t);
            } catch (Exception e) {
                return def;
            }
        };
    }
}
