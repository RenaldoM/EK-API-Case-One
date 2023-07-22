package com.rm.ekapi.caseone;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/einkaufsliste")
public class EinkaufslisteController {

    private final EinkaufslisteService service;

    public EinkaufslisteController(EinkaufslisteService service) {
        this.service = service;
    }

    @GetMapping
    public List<EinkaufslisteEintrag> getAll() {
        return service.getAll();
    }

    @PostMapping
    public EinkaufslisteEintrag create(@RequestBody EinkaufslisteEintrag eintrag) {
        return service.create(eintrag);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EinkaufslisteEintrag> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EinkaufslisteEintrag> update(@PathVariable String id, @RequestBody EinkaufslisteEintrag eintrag) {
        return service.update(id, eintrag)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
