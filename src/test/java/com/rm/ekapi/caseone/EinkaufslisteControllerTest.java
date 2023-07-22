package com.rm.ekapi.caseone;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class EinkaufslisteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EinkaufslisteService service;

    @Test
    public void getAllTest() throws Exception {
        EinkaufslisteEintrag eintrag = new EinkaufslisteEintrag();
        eintrag.setObjektBezeichnung("Apfel");
        eintrag.setMenge(5);
        eintrag.setErsteller("Max");
        eintrag.setErstellungsDatum(LocalDate.now());
        eintrag.setFaelligkeitsDatum(LocalDate.now().plusDays(1));

        List<EinkaufslisteEintrag> eintraege = Arrays.asList(eintrag);

        Mockito.when(service.getAll()).thenReturn(eintraege);

        mockMvc.perform(get("/einkaufsliste"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'objektBezeichnung': 'Apfel', 'menge': 5, 'ersteller': 'Max'}]"));
    }
}
