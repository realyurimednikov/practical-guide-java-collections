package me.mednikov.pgjc.basics;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Consumer;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

class IteratorTest {
    
    @Test
    void iterateElementsWithIteratorTest(){
        List<String> cities = List.of("Belgrade", "Cairo", "Dublin", "Moscow", "Prague");
        
        Iterator<String> iterator = cities.iterator();
        
        while(iterator.hasNext()){
            String city = iterator.next();
            System.out.println(city);
        }
        
        /*
        Iterator cannot be used once consumed
        */
        Assertions.assertThatThrownBy(() -> iterator.next())
                .isInstanceOf(NoSuchElementException.class);
    }
    
    
    @Test
    void consumeElementWithIteratorTest(){
        Consumer<Integer> function = n -> System.out.println(n);
        
        Set<Integer> numbers = Set.of(1,5, 53, 12, 95);
        
        Iterator<Integer> iterator = numbers.iterator();
        
        iterator.forEachRemaining(function);
    }
    
    @Test
    void removeElementWithIteratorTest(){
        /*
        Works with mutable collections only
        */
        Set<Integer> numbers = new HashSet<>();
        
        numbers.add(1);
        numbers.add(5);
        numbers.add(14);
        numbers.add(8);
        
        Iterator<Integer> iterator = numbers.iterator();
        
        while (iterator.hasNext()){
            iterator.next();
            iterator.remove();
        }
        
        Assertions.assertThat(numbers).isEmpty();
        
        /*
        With immutable throws UnsupportedOperationException
        */
        List<String> cities = List.of("Belgrade", "Cairo", "Dublin", "Moscow", "Prague");
        
        Iterator<String> iterator2 = cities.iterator();
        
        Assertions.assertThatThrownBy(() -> {
            while (iterator2.hasNext()){
                iterator2.next();
                iterator2.remove();
            }
        }).isInstanceOf(UnsupportedOperationException.class);
        
        
    }
}
