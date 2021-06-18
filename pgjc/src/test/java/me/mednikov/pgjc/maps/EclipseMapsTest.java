package me.mednikov.pgjc.maps;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import me.mednikov.pgjc.common.Customer;
import me.mednikov.pgjc.common.Invoice;
import me.mednikov.pgjc.common.Student;
import org.assertj.core.api.Assertions;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.multimap.Multimap;
import org.testng.annotations.Test;

class EclipseMapsTest {
    
    @Test
    void createMapTest(){
        ImmutableMap<Student, List<String>> courses = Maps.immutable.of(new Student("Andrea"), List.of("Principles of Management"));
        Assertions.assertThat(courses.containsKey(new Student("Andrea"))).isTrue();
    }
    
    @Test
    void createFromJavaTest(){
        HashMap<Student, List<String>> courses =  new HashMap<>();
        courses.put(new Student("Anna"), List.of("Algebra", "Introduction to Management", "History 101"));
        courses.put(new Student("Barbora"), List.of("International Law", "European Art 201"));
        courses.put(new Student("Carol"), List.of("International Law"));
        
        ImmutableMap<Student, List<String>> coursesEclipse = Maps.immutable.ofMap(courses);
        Assertions.assertThat(coursesEclipse.keysView().size()).isEqualTo(3);
    }
    
    @Test
    void createFromListsTest(){
        ImmutableList<Invoice> invoicesList = Lists.immutable.of(
          new Invoice(1, LocalDate.of(2021, 4, 10), new Customer(1, "Acme"), BigDecimal.valueOf(1000)),
          new Invoice(2, LocalDate.of(2021, 3, 22), new Customer(2, "Betarts"), BigDecimal.valueOf(500)),
          new Invoice(3, LocalDate.of(2021, 2, 10), new Customer(1, "Acme srl"), BigDecimal.valueOf(1000)),
          new Invoice(4, LocalDate.of(2021, 1, 10), new Customer(1, "Acme srl"), BigDecimal.valueOf(1000)),
          new Invoice(5, LocalDate.of(2021, 3, 19), new Customer(3, "Wizardworld"), BigDecimal.valueOf(750)),
          new Invoice(6, LocalDate.of(2021, 2, 22), new Customer(2, "Betarts"), BigDecimal.valueOf(500))
        );
        
        Multimap<Customer, Invoice> invoicesByCustomer = invoicesList.groupBy(Invoice::getCustomer);
        MutableMap<Customer, RichIterable<Invoice>> mutableMap = invoicesByCustomer.toMap();
        
        Assertions.assertThat(mutableMap.keySet()).hasSize(3);
    }
    
    @Test
    void insertTest(){
        ImmutableMap<Student, List<String>> courses = Maps.immutable.of(new Student("Andrea"), List.of("Principles of Management"));
        Assertions.assertThat(courses.containsKey(new Student("Andrea"))).isTrue();
        ImmutableMap<Student, List<String>> courses2 = courses.newWithKeyValue(new Student("Daniela"), List.of("International Law"));
        Assertions.assertThat(courses.containsKey(new Student("Daniela"))).isFalse();
        Assertions.assertThat(courses2.containsKey(new Student("Daniela"))).isTrue();
    }
    
    @Test
    void getByKeyTest(){
        ImmutableMap<Student, List<String>> courses = Maps.immutable.of(new Student("Andrea"), List.of("Principles of Management"));
        List<String> andrea = courses.get(new Student("Andrea"));
        Assertions.assertThat(andrea).hasSize(1).contains("Principles of Management");
        
        // get if absent
        
        List<String> barbora = courses.getIfAbsentValue(new Student("Barbora"), andrea);
        Assertions.assertThat(barbora).containsAll(andrea);
    }
    
    @Test
    void replaceTest(){
        MutableMap<Student, List<String>> courses = Maps.mutable.of(new Student("Andrea"), 
                Lists.mutable.of("History 201"));
        
        courses.ifPresentApply(new Student("Andrea"), l -> l.add("C++ Programming"));
        Assertions.assertThat(courses.get(new Student("Andrea"))).contains("History 201", "C++ Programming");
    }
    
    @Test
    void deleteTest(){
        HashMap<Student, List<String>> courses =  new HashMap<>();
        courses.put(new Student("Anna"), List.of("Algebra", "Introduction to Management", "History 101"));
        courses.put(new Student("Barbora"), List.of("International Law", "European Art 201"));
        courses.put(new Student("Carol"), List.of("International Law"));
        
        ImmutableMap<Student, List<String>> coursesEclipse = Maps.immutable.ofMap(courses);
        
        ImmutableMap<Student, List<String>> result = coursesEclipse.newWithoutKey(new Student("Carol"));
        Assertions.assertThat(result.keysView().size()).isEqualTo(2);
    }
    
}
