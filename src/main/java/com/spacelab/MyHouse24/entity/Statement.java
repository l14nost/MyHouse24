package com.spacelab.MyHouse24.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Statement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20,nullable = false)
    private String number;

    @Column(length = 1000,nullable = false)
    private String comment;

    @Column(nullable = false)
    private boolean type;

    @Column(nullable = false)
    private boolean held;

    @Column(nullable = false)
    private Instant date;

    @Column(nullable = false)
    private Long sum;

    @ManyToOne
    private Staff staff;

    @ManyToOne
    private Articles articles;

    @ManyToOne
    private User user;


}
