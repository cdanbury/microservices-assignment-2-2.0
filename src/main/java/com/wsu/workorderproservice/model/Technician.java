package com.wsu.workorderproservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "technician")
public class Technician {
    @Id
    @Column(name = "technician_code")
    private String code;
    @Column(name = "technician_first_name")
    private String firstName;
    @Column(name = "technician_last_name")
    private String lastName;
    @Column(name = "technician_type")
    private String type;
    @JoinTable(name = "technician_work_permit",
            joinColumns = @JoinColumn(name = "technician_code"),
            inverseJoinColumns = @JoinColumn(name = "state_code"))
    @ManyToMany
    private Set<State> workPermits;

}
