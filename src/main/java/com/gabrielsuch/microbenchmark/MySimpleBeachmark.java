package com.gabrielsuch.microbenchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class MySimpleBeachmark {

    private int[] array;
    private List<Integer> arrayList;

    @Param({"10", "100", "1000", "10000", "100000", "1000000"})
    public int numberOfElements;

    @Setup(Level.Trial)
    public void setup() {
        this.array = new int[numberOfElements];
        this.arrayList = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < numberOfElements; i++) {
            int randomNumber = random.nextInt();
            array[i] = randomNumber;
            arrayList.add(randomNumber);
        }
    }

    @Benchmark
    public void arraysSort() {
        Arrays.sort(array);
    }

    @Benchmark
    public void collectionsSort() {
        Collections.sort(arrayList);
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(MySimpleBeachmark.class.getSimpleName())
                .threads(1)
                .forks(1)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .jvmArgs("-server")
                .build();

        new Runner(options).run();
    }

}
