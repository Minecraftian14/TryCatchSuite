package in.mcxiv.tryCatchSuite;

@FunctionalInterface
public interface SupplierProviderStep<ReturnType> {
    SupplierProvider<ReturnType> thenGet(DangerousSupplier<? extends ReturnType> supplier);

//    <T> SupplierProvider<T> thenGet(DangerousSupplier<? extends T> supplier);

    default SupplierProvider<ReturnType> thenRun(DangerousRunnable runnable) {
        return returnTypeSupplier -> {
            Try.run(runnable);
            return null;
        };
    }
}
