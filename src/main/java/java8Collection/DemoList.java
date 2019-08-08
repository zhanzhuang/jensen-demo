package java8Collection;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DemoList {


    private List<String> list = new ArrayList<>();

    @Before
    public void initData() {
        list.add("111");
        list.add("222");
        list.add("333");
        list.add("444");
    }

    /**
     * 第一种遍历方式
     * 通过 stream.iterate
     */
    @Test
    public void test1() {
        Stream.iterate(0, i -> i + 1).limit(list.size()).forEach(i -> {
            System.out.println(list.get(i));
        });
    }

    /**
     * 第二种遍历方式
     * 通过 list.stream.foreach
     */
    @Test
    public void test2() {
        list.stream().forEach(key -> {
            System.out.println(key);
        });
    }

    /**
     * 第三种遍历方式
     * 通过stream().map()处理list，并给另一个List赋值
     */
    @Test
    public void test3() {
        List<String> list2;
        list2 = list.stream().map(key -> {
            return "stream().map()处理之后" + key;
        }).collect(Collectors.toList());

        list2.stream().forEach(key -> {
            System.out.println(key);
        });
    }

}
