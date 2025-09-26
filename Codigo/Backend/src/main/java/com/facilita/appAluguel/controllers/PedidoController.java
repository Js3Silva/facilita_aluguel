package com.facilita.appAluguel.controllers;

import com.facilita.appAluguel.dto.PedidoCreateDTO;
import com.facilita.appAluguel.dto.PedidoDTO;
import com.facilita.appAluguel.dto.PedidoUpdateDTO;
import com.facilita.appAluguel.models.Pedido;
import com.facilita.appAluguel.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


/**
 * Controlador REST para gerenciamento de pedidos.
 */
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    /**
     * Cadastra um novo pedido.
     * @param pedidoCreateDTO DTO com os dados do pedido.
     * @return ResponseEntity com o PedidoDTO criado ou mensagem de erro.
     */
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarPedido(@RequestBody @Valid PedidoCreateDTO pedidoCreateDTO) {
        try {
            PedidoDTO novoPedido = pedidoService.cadastrarPedido(pedidoCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar pedido: " + e.getMessage());
        }
    }

    /**
     * Lista todos os pedidos cadastrados.
     * @return ResponseEntity com a lista de PedidoDTOs ou mensagem de erro.
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllPedidos() {
        try {
            return ResponseEntity.ok(pedidoService.getAllPedidos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar automóveis: " + e.getMessage());
        }
    }

    /**
     * Busca um pedido pelo ID.
     * @param id ID do pedido.
     * @return ResponseEntity com o PedidoDTO ou mensagem de erro.
     */
    @GetMapping("/{id}")
    public String getPedidoById(@PathVariable Long id) {
        Pedido pedido = pedidoService.getPedidoById(id);
        return pedido != null ? pedido.toDTO().toString() : "Pedudi não encontrado";
    }

    /**
     * Atualiza um pedido existente.
     * @param id ID do pedido a ser atualizado.
     * @param pedidoUpdateDTO DTO com os dados de atualização.
     * @return ResponseEntity com o PedidoDTO atualizado ou mensagem de erro.
     */
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarPedido(@PathVariable Long id, @RequestBody @Valid PedidoUpdateDTO pedidoUpdateDTO) {
        try {
            PedidoDTO pedidoAtualizado = pedidoService.atualizarPedido(id, pedidoUpdateDTO);
            return ResponseEntity.ok(pedidoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar pedido: " + e.getMessage());
        }
    }

    /**
     * Deleta um pedido pelo ID.
     * @param id ID do pedido a ser deletado.
     * @return ResponseEntity com mensagem de sucesso ou erro.
     */
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarPedido(@PathVariable Long id) {
        try {
            pedidoService.deletarPedido(id);
            return ResponseEntity.ok("Pedido deletado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar pedido: " + e.getMessage());
        }
    }

    /**
     * Aprova um pedido.
     * @param id ID do pedido a ser aprovado.
     * @return ResponseEntity com o PedidoDTO aprovado ou mensagem de erro.
     */
    @PostMapping("/aprovar/{id}")
    public ResponseEntity<?> aprovarPedido(@PathVariable Long id) {
        try {
            PedidoDTO pedidoAprovado = pedidoService.aprovarPedido(id);
            return ResponseEntity.ok(pedidoAprovado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao aprovar pedido: " + e.getMessage());
        }
    }

    /**
     * Cancela um pedido.
     * @param id ID do pedido a ser cancelado.
     * @return ResponseEntity com o PedidoDTO cancelado ou mensagem de erro.
     */
    @PostMapping("/cancelar/{id}")
    public ResponseEntity<?> cancelarPedido(@PathVariable Long id) {
        try {
            PedidoDTO pedidoCancelado = pedidoService.cancelarPedido(id);
            return ResponseEntity.ok(pedidoCancelado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cancelar pedido: " + e.getMessage());
        }
    }
}