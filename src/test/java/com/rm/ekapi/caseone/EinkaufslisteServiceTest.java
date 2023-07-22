package com.rm.ekapi.caseone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class EinkaufslisteServiceTest {

    @TempDir
    Path tempDir;

    private EinkaufslisteService service;

    @BeforeEach
    public void setUp() {
        service = new EinkaufslisteService(tempDir.resolve("einkaufsliste.txt").toFile());
    }

    @Test
    public void testCreateAndGetAll() {
        EinkaufslisteEintrag eintrag = createEintrag();

        service.create(eintrag);

        List<EinkaufslisteEintrag> eintraege = service.getAll();
        assertEquals(1, eintraege.size());
        assertEquals(eintrag, eintraege.get(0));
    }

    @Test
    public void testGetById() {
        EinkaufslisteEintrag eintrag = createEintrag();

        service.create(eintrag);

        Optional<EinkaufslisteEintrag> retrievedEintrag = service.getById(eintrag.getObjektBezeichnung());
        assertTrue(retrievedEintrag.isPresent());
        assertEquals(eintrag, retrievedEintrag.get());
    }

    @Test
    public void testUpdate() {
        EinkaufslisteEintrag eintrag = createEintrag();

        service.create(eintrag);

        EinkaufslisteEintrag updatedEintrag = new EinkaufslisteEintrag();
        updatedEintrag.setObjektBezeichnung(eintrag.getObjektBezeichnung());
        updatedEintrag.setMenge(10);
        updatedEintrag.setErsteller("Max");
        updatedEintrag.setErstellungsDatum(LocalDate.now());
        updatedEintrag.setFaelligkeitsDatum(LocalDate.now().plusDays(1));

        Optional<EinkaufslisteEintrag> result = service.update(eintrag.getObjektBezeichnung(), updatedEintrag);
        assertTrue(result.isPresent());
        assertEquals(updatedEintrag, result.get());

        Optional<EinkaufslisteEintrag> retrievedEintrag = service.getById(eintrag.getObjektBezeichnung());
        assertTrue(retrievedEintrag.isPresent());
        assertEquals(updatedEintrag, retrievedEintrag.get());
    }

    @Test
    public void testDelete() {
        EinkaufslisteEintrag eintrag = createEintrag();

        service.create(eintrag);

        service.delete(eintrag.getObjektBezeichnung());

        Optional<EinkaufslisteEintrag> retrievedEintrag = service.getById(eintrag.getObjektBezeichnung());
        assertFalse(retrievedEintrag.isPresent());
    }

    private EinkaufslisteEintrag createEintrag() {
        EinkaufslisteEintrag eintrag = new EinkaufslisteEintrag();
        eintrag.setObjektBezeichnung("Apfel");
        eintrag.setMenge(5);
        eintrag.setErsteller("Max");
        eintrag.setErstellungsDatum(LocalDate.now());
        eintrag.setFaelligkeitsDatum(LocalDate.now().plusDays(1));
        return eintrag;
    }
}
