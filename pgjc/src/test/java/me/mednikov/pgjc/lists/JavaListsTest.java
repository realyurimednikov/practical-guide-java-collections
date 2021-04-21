package me.mednikov.pgjc.lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

class JavaListsTest {
    
    @Test
    void insertionTest(){
        List<Integer> numbers = new ArrayList<>();
        
        numbers.add(1);
        numbers.addAll(List.of(5,4,3));
        Assertions.assertThat(numbers).containsExactly(1,5,4,3);
        
        numbers.add(2, 9);
        Assertions.assertThat(numbers.get(2)).isEqualTo(9);
    }
    
    @Test
    void deletionTest(){
        List<Integer> numbers = new ArrayList<>();
        numbers.addAll(List.of(1,2,3,4,5,6));
        
        // remove by index - default for lists
        numbers.remove(2);
        Assertions.assertThat(numbers).doesNotContain(3);
        
        // remove element
        numbers.remove(Integer.valueOf(2));
        Assertions.assertThat(numbers).doesNotContain(2);
    }
    
    @Test
    void sublistTest(){
        List<String> names = List.of("Alejandra", "Beatriz", "Carmen", 
                "Dolores", "Juanita", "Katarina", "Maria");
        
        List<String> sublist = names.subList(0, 5);
        
        Assertions.assertThat(sublist)
                .contains("Juanita")
                .doesNotContain("Katarina");
    }
    
    @Test
    void searchTest(){
        List<Integer> numbers = List.of(1, 52, 12, 39, 45, 98, 100, 565, 45, 5, 9);
        
        Assertions.assertThat(numbers.indexOf(45)).isEqualTo(4);

        // using lastIndexOf
        Assertions.assertThat(numbers.lastIndexOf(45)).isEqualTo(8);
    }
    
    @Test
    void filterTest(){
        List<Integer> numbers = List.of(45, 12, 80, 77, 95, 4);
        
        List<Integer> results = numbers.stream()
                .filter(n -> n%2 == 0)
                .collect(Collectors.toList());
        
        Assertions.assertThat(results).hasSize(3);
    }
    
    @Test
    void compareTest(){
        List<Integer> list1 = List.of(1, 52, 12, 39, 45, 98, 100, 565, 6, 13);
        List<Integer> list2 = List.of(1, 52, 12, 39, 45, 98, 100, 565, 6, 13);

        // Approach 1. with equals
        boolean result = list1.equals(list2);
        Assertions.assertThat(result).isTrue();

        // Approach 2. with streams
        List<Integer> list3 = List.of(1, 12, 52, 39, 45, 100, 98, 6, 13, 565);
        Assertions.assertThat(list1).isNotEqualTo(list3);

        boolean result2 = list3.stream().allMatch(number -> list1.contains(number));
        Assertions.assertThat(result2).isTrue();
    }
}
