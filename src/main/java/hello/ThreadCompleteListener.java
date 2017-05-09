package hello;

public interface ThreadCompleteListener {
    void notifyOfThreadComplete(final Runnable locker);
}

