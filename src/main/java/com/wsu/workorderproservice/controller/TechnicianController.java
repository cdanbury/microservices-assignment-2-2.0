package com.wsu.workorderproservice.controller;

import com.wsu.workorderproservice.dto.ServiceResponseDTO;
import com.wsu.workorderproservice.dto.TechnicianDTO;
import com.wsu.workorderproservice.service.TechnicianService;
import com.wsu.workorderproservice.utilities.Constants;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.wsu.workorderproservice.utilities.Constants.MESSAGE;
import static com.wsu.workorderproservice.utilities.Constants.PAGE_COUNT;
import static com.wsu.workorderproservice.utilities.Constants.RESULT_COUNT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/technicians")
public class TechnicianController {

    private final TechnicianService technicianService;

    @GetMapping
    public ResponseEntity<ServiceResponseDTO> getAllTechnicians(@RequestParam(required = false) String search,
                                                                @RequestParam(required = false, defaultValue = "1") Integer page,
                                                                @RequestParam(required = false, defaultValue = "10") Integer rpp,
                                                                @RequestParam(required = false, defaultValue = "technicianCode") String sortField,
                                                                @RequestParam(required = false, defaultValue = Constants.DESC) String sortOrder) {
        Page<TechnicianDTO> technicianDTOPage = technicianService.get(search, sortField, sortOrder, page, rpp);
        return new ResponseEntity<>(ServiceResponseDTO.builder().meta(Map.of(MESSAGE, "Successfully retrieved technicians.", PAGE_COUNT,
                        technicianDTOPage.getTotalPages(), RESULT_COUNT, technicianDTOPage.getTotalElements()))
                .data(technicianDTOPage.getContent()).build(), HttpStatus.OK);
    }
}
