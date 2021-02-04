package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.Entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {

        @Autowired
        private EmployeeRepository employeeRepository;

        public Employee postEmployee(Employee employee) {
            return employeeRepository.save(employee);
        }

        public Employee findEmployeeById(Long id) {
            return employeeRepository.getOne(id);
        }

        public void setDaysAvailable(Set<DayOfWeek> daysAvailable, Long employeeId) {
            Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
            Employee employee = optionalEmployee.orElse(null);

            if (employee == null) {
                return;
            }
            employee.setDaysAvailable(daysAvailable);
            employeeRepository.save(employee);
        }

        public List<Employee> findEmployeesForService(LocalDate date, Set<EmployeeSkill> employeeSkills) {
            List<Employee> employees = new ArrayList<>();
            List<Employee> matchedEmployees = new ArrayList<>();
            employees = employeeRepository.getEmployeesByDaysAvailable(date.getDayOfWeek());

            for (Employee employee : employees) {
                if (employee.getSkills().containsAll(employeeSkills)) {
                    matchedEmployees.add(employee);
                }
            }
            return matchedEmployees;
        }
}
