package me.mednikov.pgjc.common;

import java.util.Objects;

public final class Employee {
    
    private final Integer employeeId;
    private final String name;
    private final Department department;

    public Employee(Integer employeeId, String name, Department department) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public Department getDepartment() {
        return department;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.employeeId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Employee other = (Employee) obj;
        if (!Objects.equals(this.employeeId, other.employeeId)) {
            return false;
        }
        return true;
    }
    
    
}
