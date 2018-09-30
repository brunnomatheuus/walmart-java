package br.com.bruno.generated;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.openjdk.jcstress.Options;
import org.openjdk.jcstress.infra.collectors.TestResultCollector;
import org.openjdk.jcstress.infra.runners.Control;
import org.openjdk.jcstress.infra.runners.Runner;
import org.openjdk.jcstress.infra.runners.StateHolder;
import org.openjdk.jcstress.util.ArrayUtils;
import org.openjdk.jcstress.util.Counter;
import org.openjdk.jcstress.util.VMSupport;
import org.openjdk.jcstress.util.OpenAddressHashCounter;
import java.util.concurrent.ExecutionException;
import br.com.bruno.Teste.StressTest;
import org.openjdk.jcstress.infra.results.LongResult4;
import br.com.bruno.Teste.TesteEstoque;

public class Teste_StressTest_jcstress extends Runner<LongResult4> {

    public Teste_StressTest_jcstress(Options opts, TestResultCollector collector, ExecutorService pool) {
        super(opts, collector, pool, "br.com.bruno.Teste.StressTest");
    }

    @Override
    public void sanityCheck() throws Throwable {
        final StressTest t = new StressTest();
        final TesteEstoque s = new TesteEstoque();
        final LongResult4 r = new LongResult4();
        Collection<Future<?>> res = new ArrayList<>();
        res.add(pool.submit(new Runnable() {
            public void run() {
                t.actor1(s, r);
            }
        }));
        res.add(pool.submit(new Runnable() {
            public void run() {
                t.actor2(s, r);
            }
        }));
        res.add(pool.submit(new Runnable() {
            public void run() {
                t.actor3(s, r);
            }
        }));
        res.add(pool.submit(new Runnable() {
            public void run() {
                t.actor4(s, r);
            }
        }));
        for (Future<?> f : res) {
            try {
                f.get();
            } catch (ExecutionException e) {
                throw e.getCause();
            }
        }
    }

    @Override
    public Counter<LongResult4> internalRun() {
        StressTest test = new StressTest();
        control.isStopped = false;

        Counter<LongResult4> counter = new OpenAddressHashCounter<>();

        final AtomicReference<StateHolder<Pair>> version = new AtomicReference<>();
        version.set(new StateHolder<>(false, new Pair[0], 4));

        final AtomicInteger epoch = new AtomicInteger();

        control.isStopped = false;
        Collection<Future<?>> tasks = new ArrayList<>();
        tasks.add(pool.submit(new Runner_actor1(control, counter, test, version, epoch)));
        tasks.add(pool.submit(new Runner_actor2(control, counter, test, version, epoch)));
        tasks.add(pool.submit(new Runner_actor3(control, counter, test, version, epoch)));
        tasks.add(pool.submit(new Runner_actor4(control, counter, test, version, epoch)));

        try {
            TimeUnit.MILLISECONDS.sleep(control.time);
        } catch (InterruptedException e) {
        }

        control.isStopped = true;

        waitFor(tasks);

        return counter;
    }

    public abstract static class RunnerBase {
        final Control control;
        final Counter<LongResult4> counter;
        final StressTest test;
        final AtomicReference<StateHolder<Pair>> version;
        final AtomicInteger epoch;

        public RunnerBase(Control control, Counter<LongResult4> counter, StressTest test, AtomicReference<StateHolder<Pair>> version, AtomicInteger epoch) {
            this.control = control;
            this.counter = counter;
            this.test = test;
            this.version = version;
            this.epoch = epoch;
        }

        public void newEpoch(StateHolder<Pair> holder) {
            Pair[] pairs = holder.pairs;
            int len = pairs.length;

            for (Pair p : pairs) {
                counter.record(p.r);
            }

            int newLen = holder.hasLaggedWorkers ? Math.max(control.minStride, Math.min(len * 2, control.maxStride)) : len;

            for (Pair p : pairs) {
                LongResult4 r = p.r;
                r.r1 = 0;
                r.r2 = 0;
                r.r3 = 0;
                r.r4 = 0;
            }

            Pair[] newPairs = pairs;
            if (newLen > len) {
                newPairs = Arrays.copyOf(pairs, newLen);
                for (int c = len; c < newLen; c++) {
                    Pair p = new Pair();
                    p.r = new LongResult4();
                    newPairs[c] = p;
                }
            }

            for (Pair p : newPairs) {
                p.s = new TesteEstoque();
            }

            version.set(new StateHolder<>(control.isStopped, newPairs, 4));
        }
    }

    public static class Runner_actor1 extends RunnerBase implements Callable {
        public Runner_actor1(Control control, Counter<LongResult4> counter, StressTest test, AtomicReference<StateHolder<Pair>> version, AtomicInteger epoch) {
            super(control, counter, test, version, epoch);
        }

        public Void call() {
            int curEpoch = 0;

            StressTest lt = test;
            boolean yield = control.shouldYield;
            AtomicReference<StateHolder<Pair>> ver = version;
            AtomicInteger ep = epoch;

            while (true) {
                StateHolder<Pair> holder = ver.get();
                if (holder.stopped) {
                    return null;
                }

                Pair[] pairs = holder.pairs;

                holder.preRun(yield);

                for (Pair p : pairs) {
                    lt.actor1(p.s, p.r);
                }

                holder.postRun(yield);

                if (ep.compareAndSet(curEpoch, curEpoch + 1)) {
                    newEpoch(holder);
                }

                curEpoch++;
                while (curEpoch != ep.get()) {
                    if (yield) Thread.yield();
                }

                holder.postConsume(yield);
            }
        }
    }

    public static class Runner_actor2 extends RunnerBase implements Callable {
        public Runner_actor2(Control control, Counter<LongResult4> counter, StressTest test, AtomicReference<StateHolder<Pair>> version, AtomicInteger epoch) {
            super(control, counter, test, version, epoch);
        }

        public Void call() {
            int curEpoch = 0;

            StressTest lt = test;
            boolean yield = control.shouldYield;
            AtomicReference<StateHolder<Pair>> ver = version;
            AtomicInteger ep = epoch;

            while (true) {
                StateHolder<Pair> holder = ver.get();
                if (holder.stopped) {
                    return null;
                }

                Pair[] pairs = holder.pairs;

                holder.preRun(yield);

                for (Pair p : pairs) {
                    lt.actor2(p.s, p.r);
                }

                holder.postRun(yield);

                if (ep.compareAndSet(curEpoch, curEpoch + 1)) {
                    newEpoch(holder);
                }

                curEpoch++;
                while (curEpoch != ep.get()) {
                    if (yield) Thread.yield();
                }

                holder.postConsume(yield);
            }
        }
    }

    public static class Runner_actor3 extends RunnerBase implements Callable {
        public Runner_actor3(Control control, Counter<LongResult4> counter, StressTest test, AtomicReference<StateHolder<Pair>> version, AtomicInteger epoch) {
            super(control, counter, test, version, epoch);
        }

        public Void call() {
            int curEpoch = 0;

            StressTest lt = test;
            boolean yield = control.shouldYield;
            AtomicReference<StateHolder<Pair>> ver = version;
            AtomicInteger ep = epoch;

            while (true) {
                StateHolder<Pair> holder = ver.get();
                if (holder.stopped) {
                    return null;
                }

                Pair[] pairs = holder.pairs;

                holder.preRun(yield);

                for (Pair p : pairs) {
                    lt.actor3(p.s, p.r);
                }

                holder.postRun(yield);

                if (ep.compareAndSet(curEpoch, curEpoch + 1)) {
                    newEpoch(holder);
                }

                curEpoch++;
                while (curEpoch != ep.get()) {
                    if (yield) Thread.yield();
                }

                holder.postConsume(yield);
            }
        }
    }

    public static class Runner_actor4 extends RunnerBase implements Callable {
        public Runner_actor4(Control control, Counter<LongResult4> counter, StressTest test, AtomicReference<StateHolder<Pair>> version, AtomicInteger epoch) {
            super(control, counter, test, version, epoch);
        }

        public Void call() {
            int curEpoch = 0;

            StressTest lt = test;
            boolean yield = control.shouldYield;
            AtomicReference<StateHolder<Pair>> ver = version;
            AtomicInteger ep = epoch;

            while (true) {
                StateHolder<Pair> holder = ver.get();
                if (holder.stopped) {
                    return null;
                }

                Pair[] pairs = holder.pairs;

                holder.preRun(yield);

                for (Pair p : pairs) {
                    lt.actor4(p.s, p.r);
                }

                holder.postRun(yield);

                if (ep.compareAndSet(curEpoch, curEpoch + 1)) {
                    newEpoch(holder);
                }

                curEpoch++;
                while (curEpoch != ep.get()) {
                    if (yield) Thread.yield();
                }

                holder.postConsume(yield);
            }
        }
    }

    static class Pair {
        public TesteEstoque s;
        public LongResult4 r;
    }
}
