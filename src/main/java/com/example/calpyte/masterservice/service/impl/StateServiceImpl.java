package com.example.calpyte.masterservice.service.impl;
import com.example.calpyte.masterservice.dto.pagination.SearchCriteria;
import com.example.calpyte.masterservice.dto.pagination.TableResponse;
import com.example.calpyte.masterservice.dto.pagination.PaginationDTO;
import com.example.calpyte.masterservice.entity.Country;
import com.example.calpyte.masterservice.entity.State;
import com.example.calpyte.masterservice.masterexception.CustomException;
import com.example.calpyte.masterservice.repo.StateRepository;
import com.example.calpyte.masterservice.service.StateService;
import com.example.calpyte.masterservice.specification.CountrySpecification;
import com.example.calpyte.masterservice.specification.StateSpecification;
import com.example.calpyte.masterservice.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public TableResponse getAllStates(PaginationDTO pagination) {
        return null;
    }

    private void validate(State state) throws CustomException {
        State eState = stateRepository.findByName(state.getName());
        if (eState != null && (!Objects.equals(eState.getId(), state.getId()))) {
            throw new CustomException("Duplicate State name");
        }
    }

//
//    private void validate(State state) throws CustomException {
//        State eState = stateRepository.findByName(state.getName());
//        if (eState != null && (!Objects.equals(eState.getId(), state.getId()))) {
//            throw new CustomException("Duplicate State name");
//        }
//    }
////        if (state.getNames() != null && state.getNames().size() > 0) {
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
        }}

    @Override
    public TableResponse getStates(PaginationDTO pagination) {
        TableResponse response;
        Pageable paging = PageRequest.of(pagination.getPageNo() - 1, pagination.getPageSize());
        Page<State> statePaged = stateRepository.findAll(getSpecifications(pagination) , paging);
        if (statePaged.hasContent()) {
            List<State> stateList = statePaged.getContent();
            response = new TableResponse(pagination.getDraw(), (int) statePaged.getTotalElements(), (int) statePaged.getTotalElements(),
                    stateList);
        } else {
            response = new TableResponse(pagination.getDraw(), (int) statePaged.getTotalElements(), (int) statePaged.getTotalElements(),
                    new ArrayList<>());
        }
        return response;
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
    private Specification<State> getSpecifications(PaginationDTO pagination) {
        List<SearchCriteria> params = new ArrayList<>(pagination.getFilter());

        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map(StateSpecification::new)
                .collect(Collectors.toList());

        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i)
                    .isOrPredicate()
                    ? Specification.where(result)
                    .or(specs.get(i))
                    : Specification.where(result)
                    .and(specs.get(i));
        }

        return result;
    }



}

