package me.mednikov.pgjc.basics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

class BasicsTest {
    
    @Test
    void modifiableVsUnmodifiableTest(){
        List<Integer> unmodNumbers = List.of(1,2,3,4);
        Assertions.assertThatThrownBy(() -> unmodNumbers.add(5)).isInstanceOf(UnsupportedOperationException.class);
        
        List<Integer> modNumbers = new ArrayList<>();
        modNumbers.addAll(unmodNumbers);
        modNumbers.add(5);
        Assertions.assertThat(modNumbers).hasSize(5).contains(5);
    }
    
    @Test
    void insertTest(){
        /*
        Insertion works only with modifiable collections
        */
        
        List<Integer> numbers = new ArrayList<>();
        
        // insert single value
        numbers.add(1);
        
        // insert collections
        numbers.addAll(List.of(2,3,4,5));
        
        Assertions.assertThat(numbers).hasSize(5);
    }
    
    @Test
    void removeTest(){
        /*
        Deletion works only with modifiable collections
        */
        
        List<Integer> numbers = new ArrayList<>();
        numbers.addAll(List.of(1,2,3,4,5));
        
        /*
        Approach 1. with remove()
        
        The default behaviour for collections is to remove an OBJECT.
        
        To achieve that in a list of numbers, we pass an Integer object instead
        of a primitive
        */
        
        numbers.remove(Integer.valueOf(2));
        Assertions.assertThat(numbers).doesNotContain(2);
        
        /*
        Approach 2. with predicate - removeIf()
        */
        
        numbers.addAll(List.of(2,6,7,8,9,10));
        numbers.removeIf(number -> number %2 == 0);
        
        Assertions.assertThat(numbers).allMatch(number -> number % 2 != 0);
    }
    
    @Test
    void getStreamTest(){
        List<Integer> numbers = List.of(1,2,3,4,5,6,7,8,9,10);
        Stream<Integer> stream = numbers.stream();
        Assertions.assertThat(stream).isInstanceOf(Stream.class);
    }
    
    @Test
    void iterationTest(){
        List<Integer> numbers = List.of(1,2,3,4,5,6,7,8,9,10);
        
        /*
        Approach 1. Using iteratior and hasNext() method
        */
        
        System.out.println("\nIteration using the Iterator.hasNext() method");
        
        Iterator<Integer> iterator1 = numbers.iterator();
        while(iterator1.hasNext()){
            System.out.println(iterator1.next());
        }
        
        /*
        Approach 2. Using iterator and forEachRemaining() method
        */
        
        System.out.println("\nIteration using the Iterator.forEachRemaining() method");
        
        Iterator<Integer> iterator2 = numbers.iterator();
        iterator2.forEachRemaining(System.out::println);
        
        /*
        Approach 3. Using the built-in forEach() method
        */
        
        System.out.println("\nIteration using the forEach() method");
        
        numbers.forEach(System.out::println);
        
        /*
        Approach 4. Using streams
        */
        
        System.out.println("\nIteration using streams");
        
        Stream<Integer> stream = numbers.stream();
        stream.forEach(System.out::println);
    }
    
    @Test
    void containsTest(){
        List<Integer> numbers = List.of(1,2,3,4,5,6,7,8,9,10);
        boolean containsOne = numbers.contains(4);
        boolean containsAll = numbers.containsAll(List.of(3, 9, 7, 10));
        
        Assertions.assertThat(containsOne).isTrue();
        Assertions.assertThat(containsAll).isTrue();
    }
    
}
