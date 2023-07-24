package com.spacelab.MyHouse24.entity;

import com.spacelab.MyHouse24.enums.Page;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "security_level")
public class SecurityLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)

    private Page page;

    @ManyToMany
    @JoinTable(name = "security_level_staff", joinColumns = {
            @JoinColumn(name = "security_level_id")
    },
            inverseJoinColumns = {
                    @JoinColumn(name = "staff_id")
            })
    private List<Staff> staffList = new ArrayList<>();


}
