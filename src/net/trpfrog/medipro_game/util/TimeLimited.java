package net.trpfrog.medipro_game.util;

public interface TimeLimited {
    boolean isOutdated();
    long getDeadline();

    class Impl implements TimeLimited {
        long limit;

        public Impl(int duringMillis) {
            limit = System.currentTimeMillis() + duringMillis;
        }

        @Override
        public boolean isOutdated() {
            return System.currentTimeMillis() > limit;
        }

        @Override
        public long getDeadline() {
            return limit;
        }
    }
}
