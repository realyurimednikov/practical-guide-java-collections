package me.mednikov.pgjc.bag;

import java.util.Iterator;
import java.util.Optional;
import me.mednikov.pgjc.common.Department;
import me.mednikov.pgjc.common.Employee;
import org.assertj.core.api.Assertions;
import org.eclipse.collections.api.bag.ImmutableBag;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.set.ImmutableSet;
import org.testng.annotations.Test;

class EclipseBagTest {
    
    @Test
    void createBagTest(){
        ImmutableBag<Employee> employees = Bags.immutable.of(
                new Employee(1, "Andrea Novakova", Department.MANAGEMENT),
                new Employee(2, "Borislav Vojtek", Department.IT),
                new Employee(3, "Denisa Zizkova", Department.DELIVERY),
                new Employee(4, "Marek Ruzicka", Department.HR)
        );
        
        Assertions.assertThat(employees.size()).isEqualTo(4);
    }
    
    @Test
    void detectTest(){
        ImmutableBag<Employee> employees = Bags.immutable.of(
                new Employee(1, "Andrea Novakova", Department.MANAGEMENT),
                new Employee(2, "Borislav Vojtek", Department.IT),
                new Employee(3, "Denisa Zizkova", Department.DELIVERY),
                new Employee(4, "Marek Ruzicka", Department.HR)
        );
        
        // detect()
        Employee andrea = employees.detect(e -> e.getName().equalsIgnoreCase("Andrea Novakova"));
        Assertions.assertThat(andrea).isNotNull();
        Assertions.assertThat(andrea.getEmployeeId()).isEqualTo(1);
        Assertions.assertThat(andrea.getDepartment()).isEqualByComparingTo(Department.MANAGEMENT);
        
        // detect Optional
        Optional<Employee> petr = employees.detectOptional(e -> e.getName().equalsIgnoreCase("Petr Vodicka"));
        Assertions.assertThat(petr).isEmpty();
        
        // detect if none
        Employee jana = employees.detectIfNone(e -> e.getName().equalsIgnoreCase("Jana Dvorakova"), 
                () -> new Employee(7, "Jana Novakova", Department.IT));
        
        Assertions.assertThat(jana).isNotNull();
        Assertions.assertThat(jana.getEmployeeId()).isEqualTo(7);
        Assertions.assertThat(jana.getDepartment()).isEqualByComparingTo(Department.IT);
    }
    
    @Test
    void selectTest(){
        ImmutableBag<Employee> employees = Bags.immutable.of(
                new Employee(1, "Andrea Novakova", Department.MANAGEMENT),
                new Employee(2, "Borislav Vojtek", Department.IT),
                new Employee(3, "Denisa Zizkova", Department.IT),
                new Employee(4, "Marek Ruzicka", Department.HR)
        );
        
        // select with predicate
        ImmutableBag<Employee> itEmployees = employees.select(e -> e.getDepartment() == Department.IT);
        Assertions.assertThat(itEmployees.size()).isEqualTo(2);
        
        
        ImmutableBag<Integer> numbers = Bags.immutable.of(1,2,3,3,5,1,10,6,9,15);
        Assertions.assertThat(numbers.size()).isEqualTo(10);
        
        // select unique (only that do not have duplicates!)
        ImmutableSet<Integer> unique = numbers.selectUnique();
        Assertions.assertThat(unique.castToSet())
                .containsExactlyInAnyOrder(2,5,6,9,10,15)
                .doesNotContain(1,3); // 1 and 3 have duplicates!
        
        // select duplicates
        ImmutableBag<Integer> duplicates = numbers.selectDuplicates();
        Assertions.assertThat(duplicates.toList()).contains(1,3);
    }
    
    @Test
    void rejectTest(){
        ImmutableBag<Integer> numbers = Bags.immutable.of(1,2,3,4,5,6,7,8,9,10);
        ImmutableBag<Integer> odd = numbers.reject(e -> e % 2 == 0);
        Assertions.assertThat(odd.toList()).contains(1,3,5,7,9);
    }
    
    @Test
    void countElementTest(){
        ImmutableBag<Integer> numbers = Bags.immutable.of(1,2,1,5,1,10,6,1,6,1);
        int countOfOne = numbers.occurrencesOf(1);
        Assertions.assertThat(countOfOne).isEqualTo(5);
    }
        
    @Test
    void addTest(){
        ImmutableBag<Integer> original = Bags.immutable.of(1,2,3);
        ImmutableBag<Integer> added = original.newWith(4);
        Assertions.assertThat(original.size()).isEqualTo(3);
        Assertions.assertThat(added.size()).isEqualTo(4);
    }
    
    @Test
    void removeTest(){
        ImmutableBag<Integer> original = Bags.immutable.of(1,2,3,4,5);
        ImmutableBag<Integer> removed = original.newWithout(5);
        Assertions.assertThat(original.size()).isEqualTo(5);
        Assertions.assertThat(removed.size()).isEqualTo(4);
    }
    
    @Test
    void iteratorTest(){
        ImmutableBag<Integer> numbers = Bags.immutable.of(1,2,3,4,5);
        Iterator<Integer> iterator = numbers.iterator();
        
        int sum = 0;
        while (iterator.hasNext()){
            int val = iterator.next();
            sum += val;
        }
        
        Assertions.assertThat(sum).isEqualTo(15);
    }
}
