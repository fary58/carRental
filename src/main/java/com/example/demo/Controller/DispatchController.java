package com.example.demo.Controller;

import com.example.demo.Entity.Dispatch;
import com.example.demo.Service.DispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/dispatches")
public class DispatchController {

    private final DispatchService dispatchService;

    @Autowired
    public DispatchController(DispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    @PostMapping("/createDispatch")
    public ResponseEntity<Dispatch> createDispatch(@Valid @RequestBody Dispatch dispatch) {
        Dispatch savedDispatch = dispatchService.saveDispatch(dispatch);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDispatch);
    }

    @GetMapping("/{dispatchId}")
    public ResponseEntity<Dispatch> getDispatchById(@Valid @PathVariable Long dispatchId) {
        Dispatch dispatch = dispatchService.getDispatchById(dispatchId);
        if (dispatch != null) {
            return ResponseEntity.ok(dispatch);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public List<Dispatch> getAllDispatches() {
        return dispatchService.getAllDispatches();
    }

    @DeleteMapping("/{dispatchId}")
    public ResponseEntity<Void> deleteDispatch(@Valid @PathVariable Long dispatchId) {
        dispatchService.deleteDispatch(dispatchId);
        return ResponseEntity.noContent().build();
    }

//        @PutMapping("/updateDispatch/{dispatchId}")
//    public ResponseEntity<Dispatch> updateDispatch(@Valid @PathVariable Long dispatchId, @RequestBody Dispatch updatedDispatch) {
//        // Check if the dispatchId exists
//        Dispatch existingDispatch = dispatchService.getDispatchById(dispatchId);
//
//        if (existingDispatch != null) {
//            // Check if the vehicle associated with the dispatch exists
//            // Assuming there's a method in DispatchService to check if the vehicle exists
//            if (dispatchService.isVehicleExists(updatedDispatch.getVehicleId())) {
//                // Update dispatch details
//                updatedDispatch.setId(dispatchId);
//                Dispatch updatedDispatchResult = dispatchService.saveDispatch(updatedDispatch);
//                return ResponseEntity.ok(updatedDispatchResult);
//            } else {
//                // Vehicle not found
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//            }
//        } else {
//            // Dispatch not found
//            return ResponseEntity.notFound().build();
//        }
//    }
}
