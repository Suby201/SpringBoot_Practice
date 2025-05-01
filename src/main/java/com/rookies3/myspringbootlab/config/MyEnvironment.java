package com.rookies3.myspringbootlab.config;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class MyEnvironment {
    private String mode;
}
