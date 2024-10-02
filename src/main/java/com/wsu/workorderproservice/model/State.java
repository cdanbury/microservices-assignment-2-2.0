package com.wsu.workorderproservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "state")
public class State {
    @Id
    @Column(name = "state_code")
    private String code;
    @Column(name = "state_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
}
