package me.mednikov.pgjc.streams;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import me.mednikov.pgjc.common.Customer;
import me.mednikov.pgjc.common.Invoice;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

class TerminalOperationsTest {
    
    @Test
    void collectToListTest(){
        List<Integer> numbers = List.of(2,3,4,5,6,7,8);
        
        Stream<Integer> stream = numbers.stream();
        
        List<Integer> result = stream
                .map(n -> n *2)
                .collect(Collectors.toList());
        
        
        Assertions.assertThat(result).containsExactly(4,6,8,10,12,14,16);
    }
    
    @Test
    void collectToMapTest(){
        List<Invoice> invoices = List.of(
          new Invoice(1, LocalDate.of(2021, Month.MARCH, 10), 
                  new Customer(1, "Acme Ltd"), BigDecimal.valueOf(1000)),
          new Invoice(1, LocalDate.of(2021, Month.APRIL, 5), 
                  new Customer(2, "Rhino Computers Ltd"), BigDecimal.valueOf(350)),
          new Invoice(1, LocalDate.of(2021, Month.JANUARY, 10), 
                  new Customer(1, "Acme Ltd"), BigDecimal.valueOf(1000)),
          new Invoice(1, LocalDate.of(2021, Month.JANUARY, 5), 
                  new Customer(2, "Rhino Computers Ltd"), BigDecimal.valueOf(400)),
          new Invoice(1, LocalDate.of(2021, Month.FEBRUARY, 12), 
                  new Customer(3, "Jack Mozer & Sons"), BigDecimal.valueOf(400)),
          new Invoice(1, LocalDate.of(2021, Month.FEBRUARY, 10), 
                  new Customer(1, "Acme Ltd"), BigDecimal.valueOf(900))
        );
        
        Stream<Invoice> stream = invoices.stream();
        
        Map<Customer, List<Invoice>> result = stream.collect(
                Collectors.groupingBy(invoice -> invoice.getCustomer())
        );
        
        Assertions.assertThat(result)
                .hasSize(3)
                .containsKey(new Customer(1, "Acme Ltd"))
                .containsKey(new Customer(2, "Rhino Computers Ltd"))
                .containsKey(new Customer(3, "Jack Mozer & Sons"));
    }
    
    @Test
    void findTest(){
        List<Invoice> invoices = List.of(
          new Invoice(1, LocalDate.of(2021, Month.MARCH, 10), 
                  new Customer(1, "Acme Ltd"), BigDecimal.valueOf(1000)),
          new Invoice(1, LocalDate.of(2021, Month.APRIL, 5), 
                  new Customer(2, "Rhino Computers Ltd"), BigDecimal.valueOf(350)),
          new Invoice(1, LocalDate.of(2021, Month.JANUARY, 10), 
                  new Customer(1, "Acme Ltd"), BigDecimal.valueOf(1000)),
          new Invoice(1, LocalDate.of(2021, Month.JANUARY, 5), 
                  new Customer(2, "Rhino Computers Ltd"), BigDecimal.valueOf(400)),
          new Invoice(1, LocalDate.of(2021, Month.FEBRUARY, 12), 
                  new Customer(3, "Jack Mozer & Sons"), BigDecimal.valueOf(400)),
          new Invoice(1, LocalDate.of(2021, Month.FEBRUARY, 10), 
                  new Customer(1, "Acme Ltd"), BigDecimal.valueOf(900))
        );
        
        Stream<Invoice> stream = invoices.stream();
        
        // use filter to find a value
        Optional<Invoice> result = stream
                .filter(invoice -> 
                        invoice.getCustomer()
                                .equals(new Customer (3, "Jack Mozer & Dons")))
                .findFirst();
        
        Assertions.assertThat(result).isNotEmpty();
    }
    
    @Test
    void matchTest(){
        List<Integer> numbers = List.of(10, 53, 23, 95, 30);
        boolean result = numbers.stream().anyMatch(n -> n%2 == 0);
        Assertions.assertThat(result).isTrue();
    }
    
    @Test
    void anyMatchTest(){
        List<Integer> numbers = List.of(10, 53, 23, 95, 30);
        boolean result = numbers.stream().anyMatch(n -> n%2 == 0);
        Assertions.assertThat(result).isTrue();
    }
    
    @Test
    void allMatchTest(){
        List<Integer> numbers = List.of(10, 53, 23, 95, 30);
        boolean result = numbers.stream().allMatch(n -> n%2 == 0);
        Assertions.assertThat(result).isFalse();
    }
    
    @Test
    void noneMatchTest(){
        List<Integer> numbers = List.of(-4, -54, -25, -8, -1);
        boolean result = numbers.stream().noneMatch(n -> n>0);
        Assertions.assertThat(result).isTrue();
    }
    
    @Test
    void reduceTest(){
        List<Invoice> invoices = List.of(
          new Invoice(1, LocalDate.of(2021, Month.MARCH, 10), 
                  new Customer(1, "Acme Ltd"), BigDecimal.valueOf(1000)),
          new Invoice(1, LocalDate.of(2021, Month.APRIL, 5), 
                  new Customer(2, "Rhino Computers Ltd"), BigDecimal.valueOf(350)),
          new Invoice(1, LocalDate.of(2021, Month.JANUARY, 10), 
                  new Customer(1, "Acme Ltd"), BigDecimal.valueOf(1000)),
          new Invoice(1, LocalDate.of(2021, Month.JANUARY, 5), 
                  new Customer(2, "Rhino Computers Ltd"), BigDecimal.valueOf(400)),
          new Invoice(1, LocalDate.of(2021, Month.FEBRUARY, 12), 
                  new Customer(3, "Jack Mozer & Sons"), BigDecimal.valueOf(400)),
          new Invoice(1, LocalDate.of(2021, Month.FEBRUARY, 10), 
                  new Customer(1, "Acme Ltd"), BigDecimal.valueOf(900))
        );
        
        Stream<Invoice> stream = invoices.stream();
        
        Map<Month, List<Invoice>> invoicesByMonth = stream.collect(
                Collectors.groupingBy(invoice -> invoice
                        .getIssuedDate()
                        .getMonth()));
        
        // use reducation to sum invoices by month
        invoicesByMonth.forEach((month, invs) -> {
            BigDecimal total = invs.stream()
                    .map(i -> i.getTotal())
                    .reduce(BigDecimal.ZERO, (subtotal, val) -> subtotal.add(val));
            
            System.out.println("Total for " + month.name()+ " : " + total.toString());
        });
    }
}
