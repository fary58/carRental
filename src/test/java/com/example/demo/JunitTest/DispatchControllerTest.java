package com.example.demo.JunitTest;

import com.example.demo.Controller.DispatchController;
import com.example.demo.Entity.Dispatch;
import com.example.demo.Service.DispatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DispatchControllerTest {

    @Mock
    private DispatchService dispatchService;

    @InjectMocks
    private DispatchController dispatchController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDispatch() {
        Dispatch dispatch = new Dispatch();
        dispatch.setId(1L);

        when(dispatchService.saveDispatch(any(Dispatch.class))).thenReturn(dispatch);

        ResponseEntity<Dispatch> responseEntity = dispatchController.createDispatch(dispatch);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1L, responseEntity.getBody().getId());
    }

    @Test
    void testGetDispatchById() {
        Dispatch dispatch = new Dispatch();
        dispatch.setId(1L);

        when(dispatchService.getDispatchById(1L)).thenReturn(dispatch);

        ResponseEntity<Dispatch> responseEntity = dispatchController.getDispatchById(1L);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1L, responseEntity.getBody().getId());
    }

    @Test
    void testGetDispatchById_NotFound() {
        when(dispatchService.getDispatchById(1L)).thenReturn(null);

        ResponseEntity<Dispatch> responseEntity = dispatchController.getDispatchById(1L);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testGetAllDispatches() {
        List<Dispatch> dispatchList = new ArrayList<>();
        dispatchList.add(new Dispatch());
        dispatchList.add(new Dispatch());

        when(dispatchService.getAllDispatches()).thenReturn(dispatchList);

        List<Dispatch> retrievedDispatches = dispatchController.getAllDispatches();

        assertNotNull(retrievedDispatches);
        assertEquals(dispatchList.size(), retrievedDispatches.size());
    }

    @Test
    void testDeleteDispatch() {
        ResponseEntity<Void> responseEntity = dispatchController.deleteDispatch(1L);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        // Verify that the deleteDispatch method was called once with the correct parameter
        verify(dispatchService, times(1)).deleteDispatch(1L);
    }
}
