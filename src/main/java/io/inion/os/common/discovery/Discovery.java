package io.inion.os.common.discovery;

import io.inion.os.common.SiDiscoveryManager;
import io.inion.os.common.SiDiscoveryManager.SiDiscoveryManagerObject;

public class Discovery implements Runnable {

  private boolean running = false;
  private volatile Thread thread;
  private String threadName = "DiscoveryThread";
  private SiDiscoveryManager discoveryManager;
  private String[] args;

  public Discovery(String... args) {
    this.discoveryManager = new SiDiscoveryManagerObject();
    this.discoveryManager.create();
    this.discoveryManager.restore(args);
  }

  public static void run(String... args) {
    Discovery discovery = new Discovery(args);
    discovery.startApplicationThread();
  }

  public boolean isRunning() {
    return running;
  }

  @Override
  public void run() {
    while (isRunning()) {
      try {
        Thread.sleep(1);
      } catch (InterruptedException ie) {}
    }
  }

  public synchronized void stop() {
    System.out.println("Halting " + threadName + "...");

    setRunning(false);

    System.out.println("Halted " + threadName + ".");
  }

  private void setRunning(boolean running) {

    this.running = running;
  }

  private void startApplicationThread(){
    thread = new Thread(this, threadName);
    setRunning(true);
    thread.start();
  }
}