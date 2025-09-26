package com.facilita.appAluguel.controllers;

import com.facilita.appAluguel.dto.AutomovelCreateDTO;
import com.facilita.appAluguel.dto.AutomovelDTO;
import com.facilita.appAluguel.dto.AutomovelUpdateDTO;
import com.facilita.appAluguel.models.Automovel;
import com.facilita.appAluguel.services.AutomovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gerenciamento de automóveis.
 */
@RestController
@RequestMapping("/automoveis")
@CrossOrigin(origins = "*")
public class AutomovelController {

    @Autowired
    private AutomovelService automovelService;

    /**
     * Cadastra um novo automóvel.
     * 
     * @param automovelCreateDTO DTO com os dados do automóvel.
     * @return ResponseEntity com o automóvel cadastrado ou mensagem de erro.
     */
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarAutomovel(@RequestBody AutomovelCreateDTO automovelCreateDTO) {
        try {
            Automovel automovel = automovelCreateDTO.toEntity(Automovel.class);
            Automovel novoAutomovel = automovelService.cadastrarAutomovel(automovel);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoAutomovel.toDTO());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar automóvel: " + e.getMessage());
        }
    }

    /**
     * Lista todos os automóveis cadastrados.
     * 
     * @return ResponseEntity com a lista de automóveis ou mensagem de erro.
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllAutomoveis() {
        try {
            return ResponseEntity.ok(automovelService.getAllAutomoveis());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar automóveis: " + e.getMessage());
        }
    }

    /**
     * Atualiza um automóvel existente.
     * 
     * @param id                 ID do automóvel a ser atualizado.
     * @param automovelUpdateDTO DTO com os dados de atualização.
     * @return String com o DTO do automóvel atualizado ou mensagem de erro.
     */
    @PutMapping("/atualizar/{id}")
    public String atualizarAutomovel(@PathVariable Long id, @RequestBody AutomovelUpdateDTO automovelUpdateDTO) {
        Automovel automovelAtualizado = automovelService.atualizarAutomovel(id, automovelUpdateDTO);
        if (automovelAtualizado == null) {
            return "Automóvel não encontrado";
        }
        return automovelAtualizado.toDTO().toString();
    }

    /**
     * Busca um automóvel pelo ID.
     * 
     * @param id ID do automóvel.
     * @return String com o DTO do automóvel ou mensagem de erro.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AutomovelDTO> getAutomovelById(@PathVariable Long id) {
        Automovel automovel = automovelService.getAutomovelById(id);

        if (automovel != null) {
            return ResponseEntity.ok(automovel.toDTO()); 
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deleta um automóvel pelo ID.
     * 
     * @param id ID do automóvel a ser deletado.
     * @return String com mensagem de sucesso ou erro.
     */
    @DeleteMapping("/deletar/{id}")
    public String deletarAutomovel(@PathVariable Long id) {
        Automovel automovel = automovelService.getAutomovelById(id);
        if (automovel != null) {
            automovelService.deletarAutomovel(id);
            return "Automóvel deletado com sucesso";
        } else {
            return "Automóvel não encontrado";
        }
    }

    /**
     * Reserva um automóvel.
     * 
     * @param id ID do automóvel a ser reservado.
     * @return ResponseEntity com mensagem de sucesso ou erro.
     */
    @PostMapping("/reservar/{id}")
    public ResponseEntity<String> reservarAutomovel(@PathVariable Long id) {
        try {
            boolean reservado = automovelService.reservarAutomovel(id);
            if (reservado) {
                return ResponseEntity.ok("Automóvel reservado com sucesso");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Automóvel não disponível para reserva");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao reservar automóvel: " + e.getMessage());
        }
    }

    /**
     * Libera um automóvel reservado.
     * 
     * @param id ID do automóvel a ser liberado.
     * @return ResponseEntity com mensagem de sucesso ou erro.
     */
    @PostMapping("/liberar/{id}")
    public ResponseEntity<String> liberarAutomovel(@PathVariable Long id) {
        try {
            boolean liberado = automovelService.liberarAutomovel(id);
            if (liberado) {
                return ResponseEntity.ok("Automóvel liberado com sucesso");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Automóvel não estava reservado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao liberar automóvel: " + e.getMessage());
        }
    }
}