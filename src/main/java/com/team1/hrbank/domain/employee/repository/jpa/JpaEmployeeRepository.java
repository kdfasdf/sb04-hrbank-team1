package com.team1.hrbank.domain.employee.repository.jpa;

import com.team1.hrbank.domain.employee.entity.Employee;
import com.team1.hrbank.domain.employee.repository.EmployeeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class JpaEmployeeRepository implements EmployeeRepository {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Employee createEmployee(Employee employee) {
    em.persist(employee);
    return employee;
  }

  @Override
  public Employee updateEmployee(Employee employee) {
    em.merge(employee);
    return employee;
  }

  @Override
  public void deleteEmployee(Employee employee) {
    em.remove(employee);
  }

  @Override
  public Employee findEmployee(long id) {
    return em.createQuery("SELECT e FROM Employee e "
            + " JOIN FETCH e.department"
            + " JOIN FETCH e.fileMetaData"
            + " WHERE e.id=:id", Employee.class)
        .setParameter("id", id)
        .getResultStream().findFirst()
        .orElse(null);
  }

  @Override
  public Optional<Employee> findEmployeeByEmployeeNumber(String employeeNumber) {
    return em.createQuery("SELECT e FROM Employee e WHERE e.employeeNumber = :employeeNumber",
            Employee.class)
        .setParameter("employeeNumber", employeeNumber)
        .getResultStream()
        .findFirst(); // Optional<Employee> 반환
  }

  @Override
  public Optional<Employee> findEmployeeByEmail(String email) {
    return em.createQuery("SELECT e FROM Employee e WHERE e.email = :email",
            Employee.class)
        .setParameter("email", email)
        .getResultStream()
        .findFirst(); // Optional<Employee> 반환
  }
}