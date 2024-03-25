package com.sapient.javams.swipeeventlistener.controller;

import com.sapient.javams.swipeeventlistener.exception.RecordNotFoundException;
import com.sapient.javams.swipeeventlistener.model.SwipeDetails;
import com.sapient.javams.swipeeventlistener.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AttendanceController {
    @Autowired
    AttendanceService service;

    @GetMapping("/totalHours/{employeeId}")
    public SwipeDetails getTotalHoursForEmployee(@PathVariable int employeeId) {
        final SwipeDetails totalOfficeHoursDetails = service.getTotalOfficeHours(employeeId);
        if (totalOfficeHoursDetails == null)
            throw new RecordNotFoundException("No record found for the given id");
        return totalOfficeHoursDetails;
    }
}
