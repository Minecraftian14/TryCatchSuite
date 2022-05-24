package in.mcxiv.tryCatchSuite.interfaces;

import java.util.Comparator;

@FunctionalInterface
public interface DangerousComparator<ArgumentType> {
    int compare(ArgumentType o1, ArgumentType o2) throws Exception;

    default Comparator<ArgumentType> defaultWrap() {
        return definedWrap(0);
    }

    default Comparator<ArgumentType> definedWrap(int def) {
        return (o1, o2) -> {
            try {
                return compare(o1, o2);
            } catch (Exception e) {
                return def;
            }
        };
    }
}
