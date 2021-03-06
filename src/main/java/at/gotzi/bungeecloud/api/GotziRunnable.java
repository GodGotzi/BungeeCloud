package at.gotzi.bungeecloud.api;

public abstract class GotziRunnable {

    private Thread thread;
    private boolean stop;

    public GotziRunnable() {
    }

    public abstract void run();

    public void runTaskAsync() {
        this.thread = new Thread(() -> {
            if (stop)
                return;
            this.run();
        });
        this.thread.start();
    }

    public void runTaskLater(int milliseconds) {
        this.thread = new Thread(() -> {
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (stop)
                return;
            this.run();
        });
        this.thread.start();
    }

    public void cancel() {
        this.stop = true;
    }

    public Thread getThread() {
        return this.thread;
    }

    public void runRepeatingTaskAsync(int milliseconds) {
        this.thread = new Thread(() -> {
            while(true) {
                if (stop)
                    return;
                try {
                    Thread.sleep(milliseconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.run();
            }
        });
        this.thread.start();
    }
}
