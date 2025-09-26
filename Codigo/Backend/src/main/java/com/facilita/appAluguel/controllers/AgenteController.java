package com.facilita.appAluguel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facilita.appAluguel.dto.AgenteCreateDTO;
import com.facilita.appAluguel.dto.AgenteUpdateDTO;
import com.facilita.appAluguel.dto.LoginDTO;
import com.facilita.appAluguel.models.Agente;
import com.facilita.appAluguel.services.AgenteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/agentes")
@CrossOrigin(origins = "*")
public class AgenteController {

    @Autowired
    private AgenteService agenteService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarAgente(@RequestBody AgenteCreateDTO agenteCreateDTO) {
        try {
            Agente agente = agenteCreateDTO.toEntity(Agente.class);
            Agente novoAgente = agenteService.cadastrarAgente(agente);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoAgente.toDTO());

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar agente: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginAgente(@RequestBody LoginDTO loginDTO) {
        String token = agenteService.login(loginDTO);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAgentes() {
        try {
            return ResponseEntity.ok(agenteService.getAllAgentes());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar agentes: " + e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    public String atualizarAgente(@PathVariable Long id, @RequestBody AgenteUpdateDTO agenteUpdateDTO) {
        Agente agenteAtualizado = agenteService.atualizarAgente(id, agenteUpdateDTO);

        if (agenteAtualizado == null) {
            return "Agente nao encontrado";
        }

        return agenteAtualizado.toDTO().toString();
    }

    @GetMapping("/{id}")
    public String getAgenteById(@PathVariable Long id) {
        Agente agente = agenteService.getAgenteById(id);
        return agente != null ? agente.toDTO().toString() : "Agente não encontrado";
    }

    @DeleteMapping("/deletar/{id}")
    public String deletarAgente(@PathVariable Long id) {
        Agente agente = agenteService.getAgenteById(id);
        if (agente != null) {
            agenteService.deletarAgente(id);
            return "Agente deletado com sucesso";
        } else {
            return "Agente não encontrado";
        }
    }
}
