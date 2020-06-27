package com.alpha.task3.controller;

import com.alpha.task3.model.OfficeDescription;
import com.alpha.task3.service.OfficeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/branches")
@AllArgsConstructor
public class OfficeController {

    private final OfficeService officeService;

    @GetMapping("/{id}")
    public ResponseEntity<OfficeDescription> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(officeService.findById(id));
    }

    @GetMapping
    public ResponseEntity<OfficeDescription> findById(@RequestParam Double lat, @RequestParam Double lon) {
        return ResponseEntity.ok(officeService.findNearest(lat, lon));
    }
}
