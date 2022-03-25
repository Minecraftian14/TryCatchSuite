package in.mcxiv.tryCatchSuite;

@FunctionalInterface
public interface DangerousFunction<ArgumentType, ReturnType> {
    ReturnType apply(ArgumentType a) throws Exception;
}
