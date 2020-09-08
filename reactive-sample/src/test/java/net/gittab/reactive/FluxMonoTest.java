package net.gittab.reactive;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

/**
 * FluxMonoTest.
 *
 * @author rookiedev
 * @date 2020/9/7 3:46 下午
 **/
public class FluxMonoTest {

    @Test
    public void fluxTest(){
        Flux.range(1, 40).buffer(20).subscribe(System.out::println);
        System.out.println("=====================");
        Flux.interval(Duration.ofSeconds(1L)).buffer(Duration.ofMillis(1000)).take(2).toStream().forEach(System.out::println);
        System.out.println("=====================");
        Flux.range(1, 10).bufferUntil(i -> i % 2 == 0).subscribe(System.out::println);
        System.out.println("=====================");
        Flux.range(1, 10).bufferWhile(i -> i % 2 == 0).subscribe(System.out::println);
        System.out.println("=====================");
        Flux.range(1, 10).filter(i -> i % 2 == 0).subscribe(System.out::println);
        System.out.println("=====================");
        Flux.range(1, 40).window(20).subscribe(item -> {
            item.subscribe(System.out::print);
            System.out.println("=====================");
        });
        System.out.println("=====================");
        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"))
                .subscribe(System.out::println);
        System.out.println("=====================");
        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"), (s1, s2) -> String.format("%s-%s", s1, s2))
                .subscribe(System.out::println);
        System.out.println("=====================");
        Flux.range(1, 1000).take(10).subscribe(System.out::println);
        System.out.println("=====================");
        Flux.range(1, 1000).takeLast(10).subscribe(System.out::println);
        System.out.println("=====================");
        Flux.range(1, 1000).takeWhile(i -> i < 10).subscribe(System.out::println);
        System.out.println("=====================");
        Flux.range(1, 1000).takeUntil(i -> i == 10).subscribe(System.out::println);
        System.out.println("=====================");
        Flux.range(1, 100).reduce((x, y) -> x + y).subscribe(System.out::println);
        System.out.println("=====================");
        Flux.range(1, 100).reduceWith(() -> 100, (x, y) -> x + y).subscribe(System.out::println);
        System.out.println("=====================");
        Flux.merge(Flux.interval(Duration.ofMillis(0), Duration.ofMillis(100)).take(5), Flux.interval(Duration.ofMillis(50), Duration.ofMillis(100)).take(5))
                .toStream()
                .forEach(System.out::println);
        System.out.println("=====================");
        Flux.mergeSequential(Flux.interval(Duration.ofMillis(0), Duration.ofMillis(100)).take(5), Flux.interval(Duration.ofMillis(50), Duration.ofMillis(100)).take(5))
                .toStream()
                .forEach(System.out::println);
        System.out.println("=====================");
        Flux.just(5, 10)
                .flatMap(x -> Flux.interval(Duration.ofMillis(x * 10), Duration.ofMillis(100)).take(x))
                .toStream()
                .forEach(System.out::println);
        System.out.println("=====================");
        Flux.just(5, 10).subscribe(System.out::println);
        System.out.println("=====================");
        Flux.just(5, 10)
                .concatMap(x -> Flux.interval(Duration.ofMillis(x * 10), Duration.ofMillis(100)).take(x))
                .toStream()
                .forEach(System.out::println);
        System.out.println("=====================");
        Flux.combineLatest(
                Arrays::toString,
                Flux.interval(Duration.ofMillis(50), Duration.ofMillis(100)).take(5),
                Flux.interval(Duration.ofMillis(100)).take(5)
        ).toStream().forEach(System.out::println);
        System.out.println("=====================");
        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalStateException()))
                .subscribe(System.out::println, System.err::println);
        System.out.println("=====================");
        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalStateException()))
                .onErrorReturn(0)
                .subscribe(System.out::println);
        System.out.println("=====================");
        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalArgumentException())).onErrorResume(e -> {
                    if (e instanceof IllegalStateException) {
                        return Mono.just(0);
                    } else if (e instanceof IllegalArgumentException) {
                        return Mono.just(-1);
                    }
                    return Mono.empty();
                })
                .subscribe(System.out::println);
        System.out.println("=====================");
        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalStateException()))
                .retry(1)
                .subscribe(System.out::println);
        System.out.println("=====================");
    }

    @Test
    public void monoTest(){
        Flux.just("Hello", "World").subscribe(System.out::println);
        Flux.fromArray(new Integer[] {1, 2, 3}).subscribe(System.out::println);
        Flux.empty().subscribe(System.out::println);
        System.out.println("=====================");
        Flux.range(1, 10).subscribe(System.out::println);
        System.out.println("=====================");
//        Flux.interval(Duration.of(10, ChronoUnit.MILLIS)).subscribe(System.out::println);
        // TimeUnit.SECONDS.sleep(3);
        System.out.println("=====================");
        Flux.generate(sink -> {
            sink.next("Hello");
            sink.complete();
        }).subscribe(System.out::println);

        final Random random = new Random();
        Flux.generate(ArrayList::new, (list, sink) -> {
            int value = random.nextInt(100);
            list.add(value);
            sink.next(value);
            if (list.size() == 10) {
                sink.complete();
            }
            return list;
        }).subscribe(System.out::println);
        System.out.println("=====================");
        Flux.create(sink -> {
            for (int i = 0; i < 10; i++){
                sink.next(i);
            }
            sink.complete();
        }).subscribe(System.out::println);

        Mono.fromSupplier(() -> "Hello").subscribe(System.out::println);
        Mono.justOrEmpty(Optional.of("Hello")).subscribe(System.out::println);
        Mono.create(sink -> sink.success("Hello")).subscribe(System.out::println);
    }

    @Test
    public void schedulerTest(){
        Flux.create(sink -> {
            sink.next(Thread.currentThread().getName());
            sink.complete();
        })
                .publishOn(Schedulers.single())
                .map(x -> String.format("[%s] %s", Thread.currentThread().getName(), x))
                .publishOn(Schedulers.elastic())
                .map(x -> String.format("[%s] %s", Thread.currentThread().getName(), x))
                .subscribeOn(Schedulers.parallel())
                .toStream()
                .forEach(System.out::println);
    }

    @Test
    public void reactiveTest(){
        StepVerifier.create(Flux.just("a", "b"))
                .expectNext("a")
                .expectNext("b")
                .verifyComplete();
        StepVerifier.withVirtualTime(() -> Flux.interval(Duration.ofHours(4), Duration.ofDays(1)).take(2))
                .expectSubscription()
                .expectNoEvent(Duration.ofHours(4))
                .expectNext(0L)
                .thenAwait(Duration.ofDays(1))
                .expectNext(1L)
                .verifyComplete();

        Hooks.onErrorDropped(providedHook -> providedHook.fillInStackTrace());

        Flux.range(1, 2).subscribe(System.out::println);

        Flux.just(1, 0).map(x -> 1 / x).checkpoint("test").subscribe(System.out::println);
    }

    @Test
    public void sourceTest() throws InterruptedException {
        final Flux<Long> source = Flux.interval(Duration.ofMillis(1000))
                .take(10)
                .publish()
                .autoConnect();
        source.subscribe();
        Thread.sleep(5000);
        source
                .toStream()
                .forEach(System.out::println);
    }

    @Test
    public void reactiveTest1(){

    }

//    private void test(){
//        var a = "1";
//        var b = 10;
//        var c = 10L;
//        System.out.println(String.format("a: %s, b: %s, c: %s", a, b + "", c + ""));
//    }
}
