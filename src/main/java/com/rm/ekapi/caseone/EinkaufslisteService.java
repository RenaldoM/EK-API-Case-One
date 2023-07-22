package com.rm.ekapi.caseone;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EinkaufslisteService {

    private final Map<String, EinkaufslisteEintrag> eintraege = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File file = new File("einkaufsliste.txt");

    public EinkaufslisteService() {
        if (file.exists()) {
            try {
                CollectionType type = objectMapper.getTypeFactory().constructCollectionType(List.class, EinkaufslisteEintrag.class);
                List<EinkaufslisteEintrag> list = objectMapper.readValue(file, type);
                list.forEach(eintrag -> eintraege.put(eintrag.getObjektBezeichnung(), eintrag));
            } catch (IOException e) {
                throw new RuntimeException("Fehler beim Laden der Einkaufsliste", e);
            }
        }
    }

    public List<EinkaufslisteEintrag> getAll() {
        return new ArrayList<>(eintraege.values());
    }

    public EinkaufslisteEintrag create(EinkaufslisteEintrag eintrag) {
        eintraege.put(eintrag.getObjektBezeichnung(), eintrag);
        saveToFile();
        return eintrag;
    }

    public Optional<EinkaufslisteEintrag> getById(String id) {
        return Optional.ofNullable(eintraege.get(id));
    }

    public Optional<EinkaufslisteEintrag> update(String id, EinkaufslisteEintrag eintrag) {
        if (eintraege.containsKey(id)) {
            eintraege.put(id, eintrag);
            saveToFile();
            return Optional.of(eintrag);
        } else {
            return Optional.empty();
        }
    }

    public void delete(String id) {
        eintraege.remove(id);
        saveToFile();
    }

    private void saveToFile() {
        try {
            objectMapper.writeValue(file, new ArrayList<>(eintraege.values()));
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Speichern der Einkaufsliste", e);
        }
    }
}
