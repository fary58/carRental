package com.example.demo.Service;

import com.example.demo.Entity.Dispatch;

import java.util.List;

public interface DispatchService {
    Dispatch saveDispatch(Dispatch dispatch);

    Dispatch getDispatchById(Long id);

    List<Dispatch> getAllDispatches();

    void deleteDispatch(Long id);
    List<Dispatch> saveAllDispatches(List<Dispatch> dispatches);

    // Additional methods if needed
}
