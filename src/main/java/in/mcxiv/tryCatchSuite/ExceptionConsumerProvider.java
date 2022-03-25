package in.mcxiv.tryCatchSuite;

@FunctionalInterface
public interface ExceptionConsumerProvider {

    ExceptionConsumer doNothing = exception -> {
    };

    void catchThis(ExceptionConsumer exceptionConsumer);

    default void doNothing() {
        catchThis(doNothing);
    }
}
