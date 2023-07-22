package com.rm.ekapi.caseone;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EinkaufslisteEintrag {
    private String objektBezeichnung;
    private int menge;
    private String ersteller;
    private LocalDate erstellungsDatum;
    private LocalDate faelligkeitsDatum;
}
