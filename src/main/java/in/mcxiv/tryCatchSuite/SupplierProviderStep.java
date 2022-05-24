package in.mcxiv.tryCatchSuite;

import in.mcxiv.tryCatchSuite.interfaces.DangerousRunnable;
import in.mcxiv.tryCatchSuite.interfaces.DangerousSupplier;

@FunctionalInterface
public interface SupplierProviderStep<ReturnType> {
    SupplierProvider<ReturnType> thenGet(DangerousSupplier<? extends ReturnType> supplier);

    default SupplierProvider<ReturnType> thenRun(DangerousRunnable runnable) {
        return returnTypeSupplier -> {
            Try.run(runnable);
            return null;
        };
    }
}
