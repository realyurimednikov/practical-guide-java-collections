package me.mednikov.pgjc.streams;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import me.mednikov.pgjc.common.Department;
import me.mednikov.pgjc.common.Employee;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

class IntermediateOperationsTest {
    
    @Test
    void filterTest(){
        List<Employee> employees = List.of(
                new Employee(1, "Mia Smith", Department.MANAGEMENT),
                new Employee(2, "Ava Williams", Department.HR),
                new Employee(3, "Isla Johnson", Department.IT),
                new Employee(4, "Isabella Brown", Department.IT),
                new Employee(5, "Jessica Williams", Department.MANAGEMENT)
        );
        
        /*
        Filter number of employees in the IT department
        */
        
        Stream<Employee> stream = employees.stream();
        
        long result = stream.filter(emp -> emp.getDepartment() == Department.IT)
                .count();
        
        Assertions.assertThat(result).isEqualTo(2);
    }
    
    @Test
    void mapTest(){
        List<Employee> employees = List.of(
                new Employee(1, "Mia Smith", Department.MANAGEMENT),
                new Employee(2, "Ava Williams", Department.HR),
                new Employee(3, "Isla Johnson", Department.IT),
                new Employee(4, "Isabella Brown", Department.IT),
                new Employee(5, "Jessica Williams", Department.MANAGEMENT)
        );
        
        /*
        Map each employee entity as a name string
        */
        
        Stream<Employee> stream = employees.stream();
        
        List<String> names = stream.map(emp -> emp.getName())
                .collect(Collectors.toList());
        
        Assertions.assertThat(names).contains(
                "Mia Smith", "Ava Williams", "Isla Johnson",
                "Isabella Brown", "Jessica Williams"
        );
    }
    
    @Test
    void flatMapTest(){
        List<Integer> points1 = List.of(1,2);
        List<Integer> points2 = List.of(3,4);
        List<Integer> points3 = List.of(5,6);
        
        List<List<Integer>> list = List.of(points1,points2,points3);
        
        List<Integer> results = list.stream()
                .flatMap(v -> v.stream())
                .collect(Collectors.toList());
        
        Assertions.assertThat(results).contains(1,2,3,4,5,6);
    }
    
    @Test
    void distinctTest(){
        List<Integer> numbers = List.of(1, 1, 2, 3, 3, 4, 5, 5); 
        
        Stream<Integer> stream = numbers.stream();
        
        long result = stream.distinct().count();
        
        Assertions.assertThat(result).isEqualTo(5);
    }
    
    @Test
    void sortTest(){
        List<Integer> numbers = List.of(-9, -18, 0, 25, 4); 
        
        Stream<Integer> stream = numbers.stream();
        
        List<Integer> result = stream.sorted().collect(Collectors.toList());
        
        Assertions.assertThat(result)
                .containsAll(numbers)
                .containsExactly(-18, -9, 0, 4, 25);
    }
    
    @Test
    void whileTest(){
        Set<Integer> numbers = Set.of(1,2,3,4,5,6,7,8);
        
        Stream<Integer> stream = numbers.stream();
        
        // don't forget to sort!
        Set<Integer> result = stream
                .sorted()
                .takeWhile(x-> x < 5)
                .collect(Collectors.toSet());
        
        Assertions.assertThat(result).hasSize(4);
    }
    
      
    @Test
    void peekTest(){
        IntStream stream = IntStream.of(2,3,4);
        
        int result = stream.peek(n -> {
            int value = n * 2;
            System.out.println(value);
        }).sum();
        
        Assertions.assertThat(result).isEqualTo(9);
        
    }
    
    @Test
    void peekVsMapTest(){
        IntStream stream = IntStream.of(2,3,4);
        
        int result = stream.peek(n -> {
            int value = n * 2;
        }).sum();
        
        Assertions.assertThat(result).isEqualTo(9);
        
        IntStream stream2 = IntStream.of(2,3,4);
        
        int result2 = stream2.map(n -> n * 2).sum();
        
        Assertions.assertThat(result2).isEqualTo(18);
    }
}
