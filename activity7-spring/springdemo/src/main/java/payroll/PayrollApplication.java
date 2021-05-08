/**
 * Demonstrate using the curl command to list all employees.
   curl -v localhost:8080/employees | json_pp

 * Demonstrate using the curl command to find one employee by id.
   curl -v localhost:8080/employees/1 | json_pp

 * Demonstrate using the curl command to create a employee.
   curl -v -X POST localhost:8080/employees -H 'Content-Type:application/json' -d '{"name": "Samwise Gamgee", "role": "gardener"}' | json_pp

 * Demonstrate using the curl command to updating an employee.
   curl -v -X PUT localhost:8080/employees/3 -H 'Content-Type:application/json' -d '{"name": "Samwise Gamgee", "role": "ring bearer"}' | json_pp

 * Demonstrate using the curl command to delete an employee.
   curl -v -X DELETE localhost:8080/employees/1 | json_pp
 */

/**
 * ORDERS
 *
 * curl -v http://localhost:8080/orders | json_pp
 *
 * curl -v -X DELETE http://localhost:8080/orders/4/cancel | json_pp
 *
 * curl -v -X PUT localhost:8080/orders/4/complete | json_pp
 */

package payroll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PayrollApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayrollApplication.class, args);
	}

}
