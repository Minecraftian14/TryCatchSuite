package in.mcxiv.tryCatchSuite;

@FunctionalInterface
public interface ExceptionConsumer {
    void consume(Exception exception);
}
