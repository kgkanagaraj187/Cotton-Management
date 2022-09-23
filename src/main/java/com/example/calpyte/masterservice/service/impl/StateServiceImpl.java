package com.example.calpyte.masterservice.service.impl;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.entity.Country;
import com.example.calpyte.masterservice.entity.State;
import com.example.calpyte.masterservice.masterexception.CustomException;
import com.example.calpyte.masterservice.repo.StateRepository;
import com.example.calpyte.masterservice.service.StateService;
import com.example.calpyte.masterservice.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class StateServiceImpl implements StateService {

    @Autowired
    private StateRepository stateRepository;

    @Override
    public void saveState(State state) throws CustomException {
        validate(state);
        Mapper.setAuditable(state);
        stateRepository.save(state);

    }

//        if (state.getNames() != null && state.getNames().size() > 0) {
//            List<State> stateList = state.getNames().stream().map(stateName -> {
//                state.setName(stateName);
//                return state;
//            }).collect(Collectors.toList());
//            stateRepository.saveAll(stateList);
//        }else{
//            validate(state);
//            Mapper.setAuditable(state);
//            stateRepository.save(state);
//        }
//    }

    @Override
    public List<State> getAllStates(PaginationDTO pagination) {
        return stateRepository.findAll();
    }

    private void validate(State state) throws CustomException {
        State eState = stateRepository.findByName(state.getName());
        if (eState != null && (!Objects.equals(eState.getId(), state.getId()))) {
            throw new CustomException("Duplicate State name");
        }
    }

    @Override
    public List<State> getAllStates() {
        return stateRepository.findAll();
    }

    @Override
    public State findById(String id) {
        Optional<State> stateOptional = stateRepository.findById(id);
        if (stateOptional.isPresent()) {
            State state = new State();
            state.setId(stateOptional.get().getId());
            state.setName(stateOptional.get().getName());
            return state;
        }
        return null;
    }

    @Override
    public List<State> findAllById(List<String> ids) {
        return stateRepository.findAllById(ids);

    }

    @Override
    public void delete(String id) throws CustomException {
        Optional<State> stateOpt = stateRepository.findById(id);
        if (stateOpt.isPresent()) {
            State state = stateOpt.get();
        }
    }

    @Override
    public List<State> findByRevNo(Long revNo) {
        List<State> stateList = stateRepository.findByRevisionNoGreaterThan(revNo);
        return stateList.stream().map(this::copyToDTO).collect(Collectors.toList());
    }

    private State copyToDTO(State state) {
        State eState = Mapper.map(state, State.class);
        eState.setId(state.getId());
        eState.setName(state.getName());
        return eState;
    }
    }

