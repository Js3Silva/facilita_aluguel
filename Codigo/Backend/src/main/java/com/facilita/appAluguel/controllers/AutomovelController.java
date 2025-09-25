package com.facilita.appAluguel.controllers;

import com.facilita.appAluguel.dto.AutomovelDTO;
import com.facilita.appAluguel.models.Automovel;
import com.facilita.appAluguel.services.AutomovelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/automoveis")
public class AutomovelController {

    private final AutomovelService service;

    public AutomovelController(AutomovelService service) {
        this.service = service;
    }

    @GetMapping
    public List<Automovel> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Automovel> findById(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Automovel create(@RequestBody AutomovelDTO dto) {
        return service.save(dto.toEntity(Automovel.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Automovel> update(@PathVariable Long id, @RequestBody AutomovelDTO dto) {
        return service.findById(id).map(existing -> {
            Automovel updated = dto.toEntity(Automovel.class);
            updated.setId(existing.getId()); // mantém o id
            return ResponseEntity.ok(service.save(updated));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/reservar")
    public ResponseEntity<Void> reservar(@PathVariable Long id) {
        return service.reservar(id) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PostMapping("/{id}/liberar")
    public ResponseEntity<Void> liberar(@PathVariable Long id) {
        return service.liberar(id) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
