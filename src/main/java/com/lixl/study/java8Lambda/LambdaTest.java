package com.lixl.study.java8Lambda;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 地址：https://mp.weixin.qq.com/s/Joc-fWtAemuijcHyn0FlNg
 *
 * @author lixinglin
 * @date 20:08
 **/
public class LambdaTest {
    static List<Person> personList = new ArrayList<Person>();

    static {
        personList.add(new Person("Tom", 8900, "male", "New York"));
        personList.add(new Person("Jack", 7000, "male", "Washington"));
        personList.add(new Person("Lily", 7800, "female", "Washington"));
        personList.add(new Person("Anni", 8200, "female", "New York"));
        personList.add(new Person("Owen", 9500, "male", "New York"));
        personList.add(new Person("Alisa", 7900, "female", "New York"));
    }

    public static void main(String[] args) {
    yyy();
//        testStream();
    }



    //筛选员工中工资高于8000的人
    public void perstonTest() {
        List<Person> collect = personList.stream().filter(x -> x.getSalary() > 8000).collect(Collectors.toList());


        Optional<Person> max = personList.stream().max(Comparator.comparingInt(Person::getSalary));
        System.out.println("员工工资最大值：" + max.get().getSalary());


        //TODO 不改变原来集合(新建对象来玩)
        List<Person> personListNew = personList.stream().map(person -> {
            Person person1 = new Person(person.getName(), 0, "0", "");
            person1.setSalary(person.getSalary() + 1000);
            return person1;
        }).collect(Collectors.toList());

        System.out.println("改动前" + personList.get(0).getName() + "--->" + personList.get(0).getSalary());
        System.out.println("改动后" + personListNew.get(0).getName() + "--->" + personListNew.get(0).getSalary());


        //TODO 改变原来集合（在老对象上面玩）
        List<Person> collect1 = personList.stream().map(person -> {
            person.setSalary(person.getSalary() + 1000);
            return person;
        }).collect(Collectors.toList());

        System.out.println("改动前" + personList.get(0).getName() + "--->" + personList.get(0).getSalary());
        System.out.println("改动后" + collect1.get(0).getName() + "--->" + collect1.get(0).getSalary());


        //TODO 求和
        personList.stream().map(Person::getSalary).reduce((x, y) -> x + y);
        personList.stream().reduce(0, (sum, p) -> sum += p.getSalary(), (sum1, sum2) -> sum1 + sum2);
        personList.stream().reduce(0, (sum, p) -> sum += p.getSalary(), Integer::sum);

        //TODO 求最大
//        personList.stream().reduce(0, (max, p) -> max > p.getSalary() ? max : p.getSalary(), Integer::max);
//        personList.stream().reduce(0, (max, p) -> max > p.getSalary() ? max : p.getSalary(), (max1, max2) -> max1 > max2 ? max1 : max2);
//        personList.stream().reduce(0, (max, p) -> max > p.getSalary() ? max : p.getSalary(), (max1, max2) -> max1 > max2 ? max1 : max2);


    }

    /**
     * 统计（count/averaging)
     * 计数：count
     * 平均值：averagingInt、averagingLong、averagingDouble
     * 最值：maxBy、minBy
     * 求和：summingInt、summingLong、summingDouble
     * 统计以上所有：summarizingInt、summarizingLong、summarizingDouble
     */

    public static void yyy() {
        // 求总数
            Long count =                personList.stream().collect(Collectors.counting());
        // 求平均工资
            Double average =            personList.stream().collect(Collectors.averagingDouble(Person::getSalary));
        // 求最高工资
            Optional<Integer> max =     personList.stream().map(Person::getSalary).collect(Collectors.maxBy(Integer::compare));
        // 求工资之和
            Integer sum =               personList.stream().collect(Collectors.summingInt(Person::getSalary));
        // 一次性统计所有信息

        DoubleSummaryStatistics collect = personList.stream().collect(Collectors.summarizingDouble(Person::getSalary));

        System.out.println("员工总数：" + count);
        System.out.println("员工平均工资：" + average);
        System.out.println("员工工资总和：" + sum);
        System.out.println("员工工资所有统计：" + collect);
    }


    /**
     * 提取/组合
     * <p>
     * 流也可以进行合并、去重、限制、跳过等操作
     */

    public void lastTest() {
        String[] arr1 = {"a", "b", "c", "d"};
        String[] arr2 = {"d", "e", "f", "g"};

        Stream<String> stream1 = Stream.of(arr1);
        Stream<String> stream2 = Stream.of(arr2);


        // concat:合并两个流 distinct：去重
        List<String> newList = Stream.concat(stream1, stream2).distinct().collect(Collectors.toList());//流合并：[a, b, c, d, e, f, g]


        // limit：限制从流中获得前n个数据
        List<Integer> collect = Stream.iterate(1, x -> x + 2).limit(10).collect(Collectors.toList());//limit：[1, 3, 5, 7, 9, 11, 13, 15, 17, 19]


        // skip：跳过前n个数据
        List<Integer> collect2 = Stream.iterate(1, x -> x + 2).skip(1).limit(5).collect(Collectors.toList());//skip：[3, 5, 7, 9, 11]


    }


    /**
     * 排序（sorted) 中间操作。有两种排序：
     * sorted()：自然排序，流中元素需实现Comparable接口
     * sorted(Comparator com)：Comparator排序器自定义排序
     */

    public void sortTest() {

        //按照工资升序排序（自然排序）
        personList.stream().sorted(Comparator.comparing(Person::getSalary)).                              map(Person::getName).collect(Collectors.toList());

        personList.stream().sorted(Comparator.comparing(Person::getSalary).reversed()).                   map(Person::getName).collect(Collectors.toList());//倒序

        //TODO 先按工资，再按年龄升序
        personList.stream().sorted(Comparator.comparing(Person::getSalary).thenComparing(Person::getAge)).map(Person::getName).collect(Collectors.toList());


        //TODO 先按工资 、再按年龄自定义
        personList.stream().sorted(
                (p1, p2) -> {
                    if (p1.getSalary() == p2.getSalary()) {
                        return p2.getAge() - p1.getAge();
                    } else {
                        return p2.getSalary() - p1.getSalary();
                    }

                }).map(Person::getName).collect(Collectors.toList());
    }


    /**
     * 接合（joinjng)
     * 将stream中元素用特定连接符（没有的话，直接连接）连接成一个字符串
     **/

    public void joninTest() {
        String names = personList.stream().map(person -> person.getName()).collect(Collectors.joining(","));
        System.out.println("所有员工的姓名：" + names);


        List<String> list = Arrays.asList("A", "B", "C");
        String string = list.stream().collect(Collectors.joining("-"));
        System.out.println("拼接后的字符串：" + string);//拼接后的字符串：A-B-C

    }


    /**
     * 分组(partitioningBy/groupingBy)
     * <p>
     * 分区：将stream按条件分为两个Map，比如员工按薪资是否高于8000分为两部分。
     * 分组：将集合分为多个Map，比如员工按性别分组。有单级分组和多级分组。
     **/
    public void zzzz() {

        // 将员工按薪资是否高于8000分组
        Map<Boolean, List<Person>> part =               personList.stream().collect(Collectors.partitioningBy(x -> x.getSalary() > 8000));

        // 将员工按性别分组
        Map<String, List<Person>> group =               personList.stream().collect(Collectors.groupingBy(Person::getSex));

        // 将员工先按性别分组，再按地区分组
        Map<String, Map<String, List<Person>>> group2 = personList.stream().collect(Collectors.groupingBy(Person::getSex, Collectors.groupingBy(Person::getArea)));

        System.out.println("员工按薪资是否大于8000分组情况：" + part);
        System.out.println("员工按性别分组情况：" + group);
        System.out.println("员工按性别、地区：" + group2);
    }


    //TODO 收集 collect,可以说是内容最丰富的部分，字面意思--把流收集起来，最终收集成一个值（新的集合）
    public void xxx() {
        //归集（toList/toSet/toMap）toCollection/toConcurrentMap等复杂一些的用法
        List<Integer> list = Arrays.asList(1, 6, 3, 4, 6, 7, 9, 6, 20);
        List<Integer> listNew = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());//toList：[6, 4, 6, 6, 20]
        Set<Integer> set =      list.stream().filter(x -> x % 2 == 0).collect(Collectors.toSet());//toSet：[4, 20, 6]
        personList.stream().filter(person -> person.getSalary() > 800).collect(Collectors.toMap(Person::getName, person -> person));
    }


    //TODO 规约(reduce),也称缩减，顾名思义，是把一个流缩减成一个值，实现对集合求和、求乘积、求最值操作

    /**
     * 案例一：求Integer集合的元素之和、乘积和最大值。
     **/

    public void bbb() {
        List<Integer> list = Arrays.asList(1, 3, 2, 8, 11, 4);
        //1.求和方式
        list.stream().reduce((x, y) -> x + y);
        list.stream().reduce(Integer::sum);
        list.stream().reduce(0, Integer::sum);

        //2.求乘积
        list.stream().reduce((x, y) -> x * y);

        //3.求最大值
        list.stream().reduce((x, y) -> x > y ? x : y);
        list.stream().reduce(1, Integer::max);


    }


    //TODO 合并字符数组
    public void aaa() {
        List<String> list = Arrays.asList("m,k,l,a", "1,3,5,7");

        List<String> listNew = list.stream().flatMap(s -> {
            String[] split = s.split(",");
            Stream<String> s2 = Arrays.stream(split);
            return s2;
        }).collect(Collectors.toList());

        System.out.println("处理前的集合：" + list);//[m-k-l-a, 1-3-5]
        System.out.println("处理后的集合：" + listNew);//[m, k, l, a, 1, 3, 5]

    }

    public void test() {
        //获取最长的字符串
        List<String> list = Arrays.asList("adnm", "admmt", "pot", "xbangd", "weoujgsd");
        Optional<String> max = list.stream().max(Comparator.comparing(String::length));
        boolean present = max.isPresent();
        if (present) {
            String s = max.get();
        }

        //获取Inter集合中最大值
        List<Integer> list1 = Arrays.asList(7, 6, 9, 4, 11, 6);

        //自然排序
        Optional<Integer> max1 = list1.stream().max(Integer::compareTo);

        //自定义排序
        Optional<Integer> max2 = list1.stream().max(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

        long count = list1.stream().filter(x -> x > 6).count();
        System.out.println("list中大于6的元素个数：" + count);

        String[] strArr = {"abcd", "bcdd", "defde", "fTr"};

        List<String> collect = Arrays.stream(strArr).map(String::toUpperCase).collect(Collectors.toList());
        System.out.println("每个元素大写：" + collect);


        List<Integer> collect1 = list1.stream().map(x -> x + 3).collect(Collectors.toList());
        System.out.println("每个元素+3：" + collect1);

    }

    private static void testStream() {
        List<Integer> list = Arrays.asList(7, 6, 9, 3, 8, 2, 1);
        // 遍历输出符合条件的元素
        Stream<Integer> stream = list.stream();

        //筛选。。。
        Stream<Integer> integerStream = stream.filter(x -> x > 6);

        integerStream.forEach(System.out::println);

        //匹配第一个
        Optional<Integer> first = integerStream.findFirst();

        //匹配任意一个(适用于并行流)
        Optional<Integer> any = integerStream.findAny();

        //是否包含符合特定条件的元素
        boolean b = stream.anyMatch(x -> x > 6);
    }
}
