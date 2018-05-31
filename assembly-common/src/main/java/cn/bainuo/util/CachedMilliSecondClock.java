package cn.bainuo.util;

/**
 * 由于System.currentTimeMillis()性能问题，系统内存缓存当前时间，每1ms更新一次
 *
 * @author DHC
 */
public enum CachedMilliSecondClock {
    /**
     * CachedMilliSecondClock实例
     */
    INS;

    private volatile long now = 0L;

    CachedMilliSecondClock(){
        now = System.currentTimeMillis();
        start();
    }

    private void start(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    now = System.currentTimeMillis();
                }
            }
        },"CachedMillisecondClockUpdater");
        t.setDaemon(true);
        t.start();
    }

    public long now(){
        return now;
    }
}
