package com.thy.casestudy.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @OneToMany(mappedBy = "department", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false)
    //One department can have many employees.
    //"mappedBy" meaning "Department" entity is not responsible for managing the foreign key (the department_id column) in the "Employee" table
    //"cascade" meaning all operations will case from department to its employees. When you save, update, or delete a Department, the same operation will be applied to its Employees.
    //"orphanRemoval" meaning if an employee is removed from the department’s list of employees, that employee will also be deleted from the database
    @JsonManagedReference  // Manages the reference to the list of Employees
    private List<Employee> employees = new ArrayList<>();

}

