package me.mednikov.pgjc.sets;

import java.util.HashSet;
import java.util.Iterator;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class JavaSetsTest {

    @Test
    void unionTest(){
        Set<Integer> set1 = Set.of(1,3,5,7,9);
        Set<Integer> set2 = Set.of(2,4,6,8, 10);
        Set<Integer> union = Stream.concat(set1.stream(), set2.stream()).collect(Collectors.toSet());
        Assertions.assertThat(union).hasSize(10);
    }

    @Test
    void intersectionTest(){
        Set<Integer> set1 = Set.of(1,2,3,4,5,6,7,8,9);
        Set<Integer> set2 = Set.of(1,3,5,7,11,13,15);
        Set<Integer> intersection = set1.stream().filter(set2::contains).collect(Collectors.toSet());
        Assertions.assertThat(intersection).hasSize(4);
    }

    @Test
    void differenceTest(){
        Set<Integer> set1 = Set.of(1,2,3,4,5,6,7,8,9);
        Set<Integer> set2 = Set.of(1,3,5,7,11,13,15);
        
        // difference set1 - set2
        Set<Integer> difference = set1.stream().filter(n -> !set2.contains(n)).collect(Collectors.toSet());
        Assertions.assertThat(difference).hasSize(5);
    }
    
    @Test
    void disjointTest(){
        Set<Integer> set1 = Set.of(1,2,3,4,5);
        Set<Integer> set2 = Set.of(6,7,8,9,10);
        
        Boolean disjoint = set1.stream().noneMatch(n -> set2.contains(n));
        Assertions.assertThat(disjoint).isTrue();
    }

    @Test
    void subsetTest(){
        SortedSet<Integer> numbers = new TreeSet<>();
        numbers.addAll(Set.of(1,2,3,4,5,6,7,8,9,10));

        // approach 1: streams
        Set<Integer> streamSubset = numbers.stream().limit(5).collect(Collectors.toSet());
        Assertions.assertThat(streamSubset).hasSize(5).contains(1,2,3,4,5);

        // approach 2: iterators
        Iterator<Integer> iterator = numbers.iterator();
        Set<Integer> iteratorSubset = new HashSet<>();
        int limit = 5;
        for (int i = 0; i<limit && iterator.hasNext(); i++){
            iteratorSubset.add(iterator.next());
        }
        Assertions.assertThat(iteratorSubset).hasSize(5).contains(1,2,3,4,5);
    }
}
