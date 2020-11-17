package net.gittab.basic;

import java.util.Arrays;
import java.util.stream.Stream;

import io.vavr.collection.List;
import io.vavr.control.Either;

/**
 * DruidWebUtils.
 *
 * @author rookiedev 2020/10/15 18:10
 **/
public class DruidWebUtils {

    public static void main(String[] args) {
        Either<Object, List<String>> stringList = Either.right(List.ofAll(Arrays.asList("a", "b", "c")));
        stringList.forEach(item -> {
            System.out.println(item + "----");
        });
        System.out.println("==============");
        stringList.flatMap(items -> {
            System.out.println("=======test=======");
            return Either.right(items.get(0));
        });

        System.out.println("==============");

        stringList.map(items -> {
//            java.util.List<String> strings = items.map(String::toUpperCase).toJavaList();
//            System.out.println(strings.toString());
            System.out.println(items);
            return items;
        });

        System.out.println("==============");
        stringList.flatMap(items -> {
            items.map(item -> {
                System.out.println(item);
                return item;
            });
            return Either.right(items);
        }).peek(items -> {
            System.out.println(items.size());
            items.peek(item -> {
                System.out.println(item);
            });
        });
        System.out.println("==============");
        Stream<String> stream = Stream.of("hello", "felord.cn");
        stream.peek(System.out::println);
        System.out.println("==============");
        Stream<String> stream1 = Stream.of("hello", "felord.cn");
        stream1.map(item -> {
            System.out.println(item);
            return item;
        });

//        .flatMap(resources -> EitherHelper.tryToEither(() -> {
//            this.resourceRepository.batchUpdateState(userId,
//                    resources.map(resource -> resource.id).toJavaList(),
//                    ResourceState.DRAFT.state);
//            return resources;
//        })).peek(resources -> resources
//                .peek(resource -> sendUnPublishEvent(resource, user)));
    }
}
