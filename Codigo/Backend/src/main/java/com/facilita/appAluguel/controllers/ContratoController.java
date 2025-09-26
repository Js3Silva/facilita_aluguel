package com.facilita.appAluguel.controllers;

import com.facilita.appAluguel.dto.ContratoCreateDTO;
import com.facilita.appAluguel.dto.ContratoDTO;
import com.facilita.appAluguel.dto.ContratoUpdateDTO;
import com.facilita.appAluguel.models.Contrato;
import com.facilita.appAluguel.services.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


/**
 * Controlador REST para gerenciamento de contratos.
 */
@RestController
@RequestMapping("/contratos")
@CrossOrigin(origins = "*")
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    /**
     * Cadastra um novo contrato associado a um pedido.
     * @param pedidoId ID do pedido associado.
     * @param contratoCreateDTO DTO com dados do contrato (ex.: empresaId).
     * @return ResponseEntity com o ContratoDTO criado ou mensagem de erro.
     */
    @PostMapping("/cadastrar/{pedidoId}")
    public ResponseEntity<?> cadastrarContrato(@PathVariable Long pedidoId, @RequestBody @Valid ContratoCreateDTO contratoCreateDTO) {
        try {
            ContratoDTO novoContrato = contratoService.criarContrato(pedidoId, contratoCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoContrato);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar contrato: " + e.getMessage());
        }
    }

    /**
     * Lista todos os contratos cadastrados.
     * @return ResponseEntity com a lista de ContratoDTOs ou mensagem de erro.
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllContratos() {
        try {
            return ResponseEntity.ok(contratoService.findAll());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar clientes: " + e.getMessage());
        }
    }

    /**
     * Busca um contrato pelo ID.
     * @param id ID do contrato.
     * @return ResponseEntity com o ContratoDTO ou mensagem de erro.
     */
    @GetMapping("/{id}")
    public String getContratoById(@PathVariable Long id) {
        Contrato contrato = contratoService.findById(id);
        return contrato != null ? contrato.toDTO().toString() : "Cliente não encontrado";
    }

    /**
     * Atualiza um contrato existente.
     * @param id ID do contrato a ser atualizado.
     * @param contratoUpdateDTO DTO com os dados de atualização.
     * @return ResponseEntity com o ContratoDTO atualizado ou mensagem de erro.
     */
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarContrato(@PathVariable Long id, @RequestBody @Valid ContratoUpdateDTO contratoUpdateDTO) {
        try {
            ContratoDTO contratoAtualizado = contratoService.atualizarContrato(id, contratoUpdateDTO);
            return ResponseEntity.ok(contratoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar contrato: " + e.getMessage());
        }
    }

    /**
     * Deleta um contrato pelo ID.
     * @param id ID do contrato a ser deletado.
     * @return ResponseEntity com mensagem de sucesso ou erro.
     */
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarContrato(@PathVariable Long id) {
        try {
            contratoService.delete(id);
            return ResponseEntity.ok("Contrato deletado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar contrato: " + e.getMessage());
        }
    }

    /**
     * Encerra um contrato, liberando o automóvel associado.
     * @param id ID do contrato a ser encerrado.
     * @return ResponseEntity com o ContratoDTO encerrado ou mensagem de erro.
     */
    @PostMapping("/encerrar/{id}")
    public ResponseEntity<?> encerrarContrato(@PathVariable Long id) {
        try {
            ContratoDTO contratoEncerrado = contratoService.encerrarContrato(id);
            return ResponseEntity.ok(contratoEncerrado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao encerrar contrato: " + e.getMessage());
        }
    }
}