package hotel.com.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hotel.com.backend.Models.Department;
import hotel.com.backend.Service.DepartmentService;

@RequestMapping("/api/departments")
@RestController
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @PostMapping("/add/{name}")
    public ResponseEntity<Department> add(@PathVariable String name) {
        return new ResponseEntity<Department>(departmentService.create(name), HttpStatus.CREATED);
    }
    @PostMapping("/id/{id}/updated/{name}")
    public ResponseEntity<Department> update(@PathVariable Long id, @PathVariable String name) {
        return new ResponseEntity<Department>(departmentService.update(id, name), HttpStatus.OK);
    }
}
