package com.example.demo.ServiceImpl;

import com.example.demo.Entity.Dispatch;
import com.example.demo.Respository.DispatchRepository;
import com.example.demo.Service.DispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DispatchServiceImpl implements DispatchService {

    private final DispatchRepository dispatchRepository;

    @Autowired
    public DispatchServiceImpl(DispatchRepository dispatchRepository) {
        this.dispatchRepository = dispatchRepository;
    }


        @Override
        public Dispatch saveDispatch(Dispatch dispatch) {
            return dispatchRepository.save(dispatch);
        }

        @Override
        public Dispatch getDispatchById(Long id) {
            return dispatchRepository.findById(id).orElse(null);
        }

        @Override
        public List<Dispatch> getAllDispatches() {
            return dispatchRepository.findAll();
        }

        @Override
        public void deleteDispatch(Long id) {
            dispatchRepository.deleteById(id);
        }

    @Override
    public List<Dispatch> saveAllDispatches(List<Dispatch> dispatches) {
        return dispatchRepository.saveAll(dispatches);
    }
    }


