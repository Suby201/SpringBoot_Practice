package com.rookies3.myspringbootlab.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
@Builder
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String studentNumber;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "student", cascade = CascadeType.ALL)
    private StudentDetail studentDetail;

    //N:1 Student와 Department 관계에서 N쪽에 해당하는 Student가 Owner이다.
    //FK 에 와 매핑되는 필드
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="department_id")
    private Department department;
}
