package me.mednikov.pgjc.maps;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import me.mednikov.pgjc.common.Customer;
import me.mednikov.pgjc.common.Invoice;
import me.mednikov.pgjc.common.Student;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

class JavaMapsTest {

    @Test
    void insertTest(){
        HashMap<Student, List<String>> courses =  new HashMap<>();
        
        courses.put(new Student("Anna"), List.of("Algebra", "Introduction to Management", "History 101"));
        courses.put(new Student("Barbora"), List.of("International Law", "European Art 201"));
        courses.put(new Student("Carol"), List.of("International Law"));
        Assertions.assertThat(courses).hasSize(3);
        
        // put() returns previously associated values
        List<String> oldCourses = courses.put(new Student("Barbora"), List.of("History 101"));
        Assertions.assertThat(oldCourses).hasSize(2);
        List<String> newCourses = courses.get(new Student("Barbora"));
        Assertions.assertThat(newCourses).hasSize(1);
        
        // put if absent
        // adds new value, only if key is not presented
        // otherwise return old values
        List<String> carolCourses = courses.putIfAbsent(new Student("Carol"), List.of("Law of Tort", "Networking Infrastructure"));
        Assertions.assertThat(carolCourses).contains("International Law").hasSize(1);
    }
    
    @Test
    void getByKeyTest(){
        List<Invoice> invoices = List.of(
          new Invoice(1, LocalDate.of(2021, Month.JUNE, 10), 
                  new Customer(1, "Acme Ltd"), BigDecimal.valueOf(1000)),
          new Invoice(1, LocalDate.of(2021, Month.APRIL, 5), 
                  new Customer(2, "Rhino Computers Ltd"), BigDecimal.valueOf(350)),
          new Invoice(1, LocalDate.of(2021, Month.APRIL, 10), 
                  new Customer(1, "Acme Ltd"), BigDecimal.valueOf(1000)),
          new Invoice(1, LocalDate.of(2021, Month.JANUARY, 5), 
                  new Customer(2, "Rhino Computers Ltd"), BigDecimal.valueOf(400)),
          new Invoice(1, LocalDate.of(2021, Month.MAY, 12), 
                  new Customer(3, "Jack Mozer & Sons"), BigDecimal.valueOf(400)),
          new Invoice(1, LocalDate.of(2021, Month.MAY, 10), 
                  new Customer(1, "Acme Ltd"), BigDecimal.valueOf(900))
        );
        
        Stream<Invoice> stream = invoices.stream();
        
        Map<Customer, List<Invoice>> invoicesByCustomer = stream.collect(
                Collectors.groupingBy(invoice -> invoice.getCustomer())
        );
        
        // get
        List<Invoice> result = invoicesByCustomer.get(new Customer(1, "Acme Ltd"));
        Assertions.assertThat(result).hasSize(3);
        
        // get or default
        List<Invoice> result2 = invoicesByCustomer.getOrDefault(new Customer(4, "Codesity OU"), List.of());
        Assertions.assertThat(result2).isEmpty();
        
    }
    
    @Test
    void deleteTest(){
        HashMap<Student, List<String>> courses =  new HashMap<>();
        courses.put(new Student("Anna"), List.of("Algebra", "Introduction to Management", "History 101"));
        courses.put(new Student("Barbora"), List.of("International Law", "European Art 201"));
        courses.put(new Student("Carol"), List.of("International Law"));
        
        // remove (k)
        // delete by key
        courses.remove(new Student("Anna"));
        Assertions.assertThat(courses).doesNotContainKey(new Student("Anna"));
        
        // remove(k,v)
        // delete only if value is equal to v
        courses.remove(new Student("Carol"), List.of("European Art 201"));
        Assertions.assertThat(courses).containsKey(new Student("Carol"));
    }
    
    @Test
    void replaceTest(){
        HashMap<Student, List<String>> courses =  new HashMap<>();
        courses.put(new Student("Anna"), List.of("Algebra", "Introduction to Management", "History 101"));
        courses.put(new Student("Barbora"), List.of("International Law", "European Art 201"));
        courses.put(new Student("Carol"), List.of("International Law"));
        
        // replace(k,v)
        courses.replace(new Student("Carol"), List.of("Programming in Java", "Algorithms and Data Structures"));
        Assertions.assertThat(courses.get(new Student("Carol")))
                .hasSize(2)
                .contains("Programming in Java", "Algorithms and Data Structures");
        
        // replace(k, ov, nv)
        boolean result = courses.replace(new Student("Carol"), List.of("International Law"), List.of("Law of Tort"));
        Assertions.assertThat(result).isFalse();
    }
}
