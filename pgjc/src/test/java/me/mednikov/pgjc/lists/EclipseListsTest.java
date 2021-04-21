package me.mednikov.pgjc.lists;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import me.mednikov.pgjc.common.Customer;
import me.mednikov.pgjc.common.Department;
import me.mednikov.pgjc.common.Employee;
import me.mednikov.pgjc.common.Invoice;
import me.mednikov.pgjc.common.Student;
import org.assertj.core.api.Assertions;
import org.eclipse.collections.api.block.function.primitive.DoubleFunction;
import org.eclipse.collections.api.block.predicate.Predicate2;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableDoubleList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.impl.factory.primitive.IntLists;
import org.testng.annotations.Test;

class EclipseListsTest {
    
    @Test
    void createLists(){
        // create lists of objects
        ImmutableList<String> names = Lists.immutable.of("Alejandra", "Beatriz", "Carmen", 
                "Dolores", "Juanita");
        Assertions.assertThat(names).hasSize(5);

        ImmutableList<String> empty = Lists.immutable.empty();
        Assertions.assertThat(empty).isEmpty();

        // create lists of primitives
        ImmutableIntList numbers = IntLists.immutable.of(1,2,3,4,5,6,7,8,9,10);
        Assertions.assertThat(numbers.size()).isEqualTo(10);
    }
    
    @Test
    void createFromJavaListTest() {
        List<String> names = List.of("Alejandra", "Beatriz", "Carmen", 
                "Dolores", "Juanita");
        
        ImmutableList<String> immutableNames = Lists.immutable.ofAll(names);
        
        Assertions.assertThat(immutableNames).containsAll(names);
    }
    
    @Test
    void createFromStreamTest() {
        IntStream stream = IntStream.rangeClosed(1, 100);
        
        ImmutableIntList list = IntLists.immutable.ofAll(stream);
        
        Assertions.assertThat(list.getFirst()).isEqualTo(1);
        
        Assertions.assertThat(list.getLast()).isEqualTo(100);
    }
    
    
    @Test
    void searchTest(){
        ImmutableList<Employee> employees = Lists.immutable.of(
                new Employee(1, "John Doe", Department.MANAGEMENT),
                new Employee(2, "", Department.DELIVERY),
                new Employee(3, "", Department.IT),
                new Employee(4, "", Department.IT),
                new Employee(5, "", Department.HR),
                new Employee(6, "", Department.IT)
        );
        
        Predicate2<Employee, Department> predicate = (emp, dep) -> emp.getDepartment() == dep;
        
        Employee hr = employees.detectWith(predicate, Department.HR);
        
        Assertions.assertThat(hr.getEmployeeId()).isEqualTo(5);
        
        Optional<Employee> delivery = employees.detectWithOptional(predicate, Department.DELIVERY);
        
        Assertions.assertThat(delivery).isNotEmpty();
    }
    
    @Test
    void filterTest() {
        ImmutableList<Student> students = Lists.immutable.of(
            new Student("Anna", 4.5), 
            new Student("Beata", 4.1), 
            new Student("Carolina", 3.9),
            new Student("Daniela", 3.5),
            new Student("Eva", 3.1));

        // filter with select()
        ImmutableList<Student> goodStudents = students.select(student -> student.getGpa() > 4.0);
        Assertions.assertThat(goodStudents).hasSize(2);

        // filter with reject()
        ImmutableList<Student> badStudents = students.reject(student -> student.getGpa() > 4.0);
        Assertions.assertThat(badStudents).hasSize(3);

        // use predicate
        Predicate2<Student, Double> predicate = (student, gpa) -> student.getGpa() > gpa;
        ImmutableList<Student> goodStudents2 = students.selectWith(predicate, 4.0);
        ImmutableList<Student> badStudents2 = students.rejectWith(predicate, 4.0);
        Assertions.assertThat(goodStudents2).hasSameElementsAs(goodStudents);
        Assertions.assertThat(badStudents2).hasSameElementsAs(badStudents);
    }
    
    @Test
    void groupTest(){
        ImmutableList<Invoice> invoices = Lists.immutable.of(
          new Invoice(1, LocalDate.of(2021, 4, 10), new Customer(1, "Acme"), BigDecimal.valueOf(1000)),
          new Invoice(2, LocalDate.of(2021, 3, 22), new Customer(2, "Betarts"), BigDecimal.valueOf(500)),
          new Invoice(3, LocalDate.of(2021, 2, 10), new Customer(1, "Acme srl"), BigDecimal.valueOf(1000)),
          new Invoice(4, LocalDate.of(2021, 1, 10), new Customer(1, "Acme srl"), BigDecimal.valueOf(1000)),
          new Invoice(5, LocalDate.of(2021, 3, 19), new Customer(3, "Wizardworld"), BigDecimal.valueOf(750)),
          new Invoice(6, LocalDate.of(2021, 2, 22), new Customer(2, "Betarts"), BigDecimal.valueOf(500))
        );
        
        Multimap<Customer, Invoice> invoicesByCustomer = invoices.groupBy(Invoice::getCustomer);
        
        Assertions.assertThat(invoicesByCustomer.get(new Customer(1, "Acme"))).hasSize(3);
        
        Multimap<Month, Invoice> invoicesByMonth = invoices.groupBy(i -> i.getIssuedDate().getMonth());
        
        Assertions.assertThat(invoicesByMonth.get(Month.MARCH)).hasSize(2);
    }
    
    @Test
    void collectTest(){
        ImmutableList<Student> students = Lists.immutable.of(
            new Student("Anna", 4.5), 
            new Student("Beata", 4.1), 
            new Student("Carolina", 3.9),
            new Student("Daniela", 3.5),
            new Student("Eva", 3.1));

        DoubleFunction<Student> gpaFunction = student -> student.getGpa();
        ImmutableDoubleList gpas = students.collectDouble(gpaFunction);
        
        Assertions.assertThat(gpas.min()).isEqualByComparingTo(3.1);
        Assertions.assertThat(gpas.max()).isEqualByComparingTo(4.5);
    }
    
    @Test
    void matchesTest() {
        ImmutableList<Integer> numbers = Lists.immutable.of(10, 53, 23, 95, 30);

        boolean allEven = numbers.allSatisfy(number -> number %2 == 0);
        Assertions.assertThat(allEven).isFalse();

        boolean atLeastOneEven = numbers.anySatisfy(number -> number %2 == 0);
        Assertions.assertThat(atLeastOneEven).isTrue();
    }
}
