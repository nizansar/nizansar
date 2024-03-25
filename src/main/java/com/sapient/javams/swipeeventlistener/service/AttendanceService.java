package com.sapient.javams.swipeeventlistener.service;


import com.sapient.javams.swipeeventlistener.model.SwipeDetails;

public interface AttendanceService {
        SwipeDetails getTotalOfficeHours(int employeeId);
}
