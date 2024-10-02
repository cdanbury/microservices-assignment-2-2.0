package com.wsu.workorderproservice.service;

import com.wsu.workorderproservice.dto.TechnicianDTO;
import com.wsu.workorderproservice.exception.DatabaseErrorException;
import com.wsu.workorderproservice.model.State;
import com.wsu.workorderproservice.model.Technician;
import com.wsu.workorderproservice.repository.TechnicianRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.wsu.workorderproservice.utilities.CommonUtils.sort;

@Service
@RequiredArgsConstructor
@Slf4j
public class TechnicianService {

    private final TechnicianRepository technicianRepository;

    /**
     * Retrieves a list of all technicians with optional filter parameters.
     * @param search - allows for type ahead search by technician firstname, lastname, workOrderStatus, or technicianCode.
     * @param sortField - field used for sorting result. Default value is descending.
     * @param sortOrder - specifies order for the returned result
     * @param page - specifies which page number to return
     * @param rpp - specifies how many results to return per page.
     * @return - Returns Page<TechnicianDTO> mapped from the Page<Object[]> returned by the database.
     */
    public Page<TechnicianDTO> get(String search, String sortField, String sortOrder, Integer page, Integer rpp) {
        try {
            Page<Object[]> technicians = technicianRepository.findBySearch(search, PageRequest.of(page-1, rpp, sort(sortField, sortOrder)));
            return technicians.map(technician -> TechnicianDTO.builder().code((String)technician[0])
                    .firstName((String)technician[1]).lastName((String)technician[2])
                    .latestWorkOrderStatus((String)technician[3]).type((String) technician[4]).workPermits(workPermits((String)technician[5])).build());
        } catch (Exception e) {
            log.error("Failed to retrieve technicians. search:{}, sortField:{}, sortOrder:{}, page:{}, rpp:{}, Exception:{}",
                    search, sortField, sortOrder, page, rpp, e);
            throw new DatabaseErrorException("Failed to retrieve technicians", e);
        }
    }

    public Set<State> workPermits(String workPermits) {
        if (!StringUtils.hasLength(workPermits)) {
            return null;
        }
        List<String> workPermitStates = Arrays.asList(workPermits.split(","));
        return workPermitStates.stream().map(state -> State.builder().code(state).build()).collect(Collectors.toSet());
    }

}
