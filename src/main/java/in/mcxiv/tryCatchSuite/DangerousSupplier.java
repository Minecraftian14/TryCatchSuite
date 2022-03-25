package in.mcxiv.tryCatchSuite;

@FunctionalInterface
public interface DangerousSupplier<ReturnType> {
    ReturnType get() throws Exception;
}
