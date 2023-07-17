package com.spacelab.MyHouse24.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Articles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100,nullable = false)
    private String name;
    @Column(length = 100,nullable = false)
    private String type;
    @OneToMany(mappedBy = "articles")
    private List<Statement> statementList = new ArrayList<>();

}
