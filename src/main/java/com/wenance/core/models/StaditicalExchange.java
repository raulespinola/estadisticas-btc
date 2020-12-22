package com.wenance.core.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StaditicalExchange {
    private LocalDateTime timeDesde;
    private LocalDateTime timeHasta;
    private double promedio;
    private double diferenciaPorcentual;
}
