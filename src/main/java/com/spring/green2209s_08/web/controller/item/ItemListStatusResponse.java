package com.spring.green2209s_08.web.controller.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ItemListStatusResponse {
    private Long total;
    private Long decisionInProcess;
    private Long reject;
    private Long blockSelling;
    private Long shutdown;
    private Long approval;
}