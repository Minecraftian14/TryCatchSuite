package in.mcxiv.tryCatchSuite;

import in.mcxiv.tryCatchSuite.interfaces.DangerousRunnable;

import java.util.function.Supplier;

@FunctionalInterface
public interface SupplierProvider<ReturnType> {

    ReturnType elseGet(Supplier<? extends ReturnType> returnTypeSupplier);

    default ReturnType elseNull() {
        return elseGet(() -> null);
    }

    default ReturnType elseThrow() {
        return elseGet(() -> {
            throw new RuntimeException();
        });
    }

    default ReturnType elseRun(DangerousRunnable runnable) {
        return elseGet(() -> {
            Try.run(runnable);
            return null;
        });
    }
}
