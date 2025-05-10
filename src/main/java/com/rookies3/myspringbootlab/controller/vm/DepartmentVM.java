package com.rookies3.myspringbootlab.controller.vm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentVM {
    private Long id;
    private String name;
    private String code;

    // 필요에 따라 다른 필드 추가 가능
}