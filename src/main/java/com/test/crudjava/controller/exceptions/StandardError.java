package com.test.crudjava.controller.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
public class StandardError implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer status;
    private String msg;
    private Long timeStamp;

}
