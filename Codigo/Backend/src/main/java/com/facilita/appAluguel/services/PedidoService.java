package com.facilita.appAluguel.services;

import com.facilita.appAluguel.dto.PedidoCreateDTO;
import com.facilita.appAluguel.dto.PedidoDTO;
import com.facilita.appAluguel.dto.PedidoUpdateDTO;
import com.facilita.appAluguel.enums.EStatusPedido;
import com.facilita.appAluguel.models.Automovel;
import com.facilita.appAluguel.models.Cliente;
import com.facilita.appAluguel.models.Pedido;
import com.facilita.appAluguel.repositories.AutomovelRepository;
import com.facilita.appAluguel.repositories.ClienteRepository;
import com.facilita.appAluguel.repositories.PedidoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Serviço para gerenciamento de pedidos.
 */
@Service
public class PedidoService {

    private static final Logger logger = LoggerFactory.getLogger(PedidoService.class);

    @Autowired
    private final PedidoRepository pedidoRepository;

    @Autowired
    private final ClienteRepository clienteRepository;

    @Autowired
    private final AutomovelRepository automovelRepository;

    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository, AutomovelRepository automovelRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.automovelRepository = automovelRepository;
    }

    /**
     * Cadastra um novo pedido.
     * @param pedidoCreateDTO DTO com os dados do pedido.
     * @return PedidoDTO do pedido criado.
     * @throws IllegalArgumentException se cliente ou automóvel não existirem ou automóvel estiver indisponível.
     */
    @Transactional
    public PedidoDTO cadastrarPedido(@Valid PedidoCreateDTO pedidoCreateDTO) {
        logger.info("Cadastrando novo pedido para cliente ID: {}, automóvel ID: {}", pedidoCreateDTO.clienteId(), pedidoCreateDTO.automovelId());
        Objects.requireNonNull(pedidoCreateDTO, "DTO de criação não pode ser nulo");

        // Validar cliente
        Cliente cliente = clienteRepository.findById(pedidoCreateDTO.clienteId())
                .orElseThrow(() -> {
                    logger.error("Cliente não encontrado com ID: {}", pedidoCreateDTO.clienteId());
                    return new IllegalArgumentException("Cliente não encontrado com ID: " + pedidoCreateDTO.clienteId());
                });

        // Validar automóvel
        Automovel automovel = automovelRepository.findById(pedidoCreateDTO.automovelId())
                .orElseThrow(() -> {
                    logger.error("Automóvel não encontrado com ID: {}", pedidoCreateDTO.automovelId());
                    return new IllegalArgumentException("Automóvel não encontrado com ID: " + pedidoCreateDTO.automovelId());
                });

        // Verificar disponibilidade do automóvel
        if (!automovel.reservar(null)) {
            logger.error("Automóvel indisponível com ID: {}", pedidoCreateDTO.automovelId());
            throw new IllegalArgumentException("Automóvel indisponível para o pedido");
        }

        // Criar pedido
        Pedido pedido = pedidoCreateDTO.toEntity(Pedido.class);
        pedido.setCliente(cliente);
        pedido.setAutomovel(automovel);
        Pedido salvo = pedidoRepository.save(pedido);
        automovelRepository.save(automovel);

        logger.info("Pedido criado com sucesso, ID: {}", salvo.getId());
        return salvo.toDTO();
    }

    /**
     * Atualiza um pedido existente.
     * @param id ID do pedido a ser atualizado.
     * @param pedidoUpdateDTO DTO com os dados de atualização.
     * @return PedidoDTO do pedido atualizado.
     * @throws PedidoNaoEncontradoException se o pedido não for encontrado.
     * @throws IllegalArgumentException se automóvel for inválido ou indisponível.
     */
    @Transactional
    public PedidoDTO atualizarPedido(Long id, @Valid PedidoUpdateDTO pedidoUpdateDTO) {
        logger.info("Atualizando pedido com ID: {}", id);
        Objects.requireNonNull(pedidoUpdateDTO, "DTO de atualização não pode ser nulo");

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Pedido não encontrado com ID: {}", id);
                    return new PedidoNaoEncontradoException(id);
                });

        if (pedidoUpdateDTO.automovelId() != null) {
            Automovel automovel = automovelRepository.findById(pedidoUpdateDTO.automovelId())
                    .orElseThrow(() -> {
                        logger.error("Automóvel não encontrado com ID: {}", pedidoUpdateDTO.automovelId());
                        return new IllegalArgumentException("Automóvel não encontrado com ID: " + pedidoUpdateDTO.automovelId());
                    });
            if (!automovel.reservar(pedido)) {
                logger.error("Automóvel indisponível com ID: {}", pedidoUpdateDTO.automovelId());
                throw new IllegalArgumentException("Automóvel indisponível para o pedido");
            }
            // Liberar automóvel anterior, se houver
            if (pedido.getAutomovel() != null && !pedido.getAutomovel().getId().equals(pedidoUpdateDTO.automovelId())) {
                pedido.getAutomovel().liberar();
                automovelRepository.save(pedido.getAutomovel());
            }
            pedido.setAutomovel(automovel);
            automovelRepository.save(automovel);
        }

        if (pedidoUpdateDTO.dataInicio() != null) {
            pedido.setDataInicio(pedidoUpdateDTO.dataInicio());
        }
        if (pedidoUpdateDTO.dataFim() != null) {
            pedido.setDataInicio(pedidoUpdateDTO.dataFim());
        }
        if (pedidoUpdateDTO.status() != null) {
            pedido.setStatus(pedidoUpdateDTO.status());
        }
        if (pedidoUpdateDTO.avaliacoesFinanceiras() != null) {
            pedido.setAvaliacoesFinanceiras(pedidoUpdateDTO.avaliacoesFinanceiras());
        }

        Pedido atualizado = pedidoRepository.save(pedido);
        logger.info("Pedido atualizado com sucesso, ID: {}", id);
        return atualizado.toDTO();
    }

    /**
     * Busca um pedido pelo ID.
     * @param id ID do pedido.
     * @return Optional contendo o pedido, se encontrado.
     */
    public Pedido getPedidoById(Long id) {
        logger.info("Buscando pedido com ID: {}", id);
        return pedidoRepository.findById(id).orElse(null);
    }

    /**
     * Lista todos os pedidos cadastrados.
     * @return Lista de pedidos.
     */
    public Iterable<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }

    /**
     * Deleta um pedido pelo ID.
     * @param id ID do pedido a ser deletado.
     * @throws PedidoNaoEncontradoException se o pedido não for encontrado.
     */
    @Transactional
    public void deletarPedido(Long id) {
        logger.info("Deletando pedido com ID: {}", id);
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Pedido não encontrado com ID: {}", id);
                    return new PedidoNaoEncontradoException(id);
                });
        if (pedido.getAutomovel() != null) {
            pedido.getAutomovel().liberar();
            automovelRepository.save(pedido.getAutomovel());
        }
        pedidoRepository.deleteById(id);
        logger.info("Pedido deletado com sucesso, ID: {}", id);
    }

    /**
     * Aprova um pedido, mudando seu status para APROVADO.
     * @param id ID do pedido.
     * @return PedidoDTO do pedido aprovado.
     * @throws PedidoNaoEncontradoException se o pedido não for encontrado.
     */
    @Transactional
    public PedidoDTO aprovarPedido(Long id) {
        logger.info("Aprovando pedido com ID: {}", id);
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Pedido não encontrado com ID: {}", id);
                    return new PedidoNaoEncontradoException(id);
                });
        pedido.setStatus(EStatusPedido.APROVADO);
        Pedido atualizado = pedidoRepository.save(pedido);
        logger.info("Pedido aprovado com sucesso, ID: {}", id);
        return atualizado.toDTO();
    }

    /**
     * Cancela um pedido, mudando seu status para CANCELADO e liberando o automóvel.
     * @param id ID do pedido.
     * @return PedidoDTO do pedido cancelado.
     * @throws PedidoNaoEncontradoException se o pedido não for encontrado.
     */
    @Transactional
    public PedidoDTO cancelarPedido(Long id) {
        logger.info("Cancelando pedido com ID: {}", id);
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Pedido não encontrado com ID: {}", id);
                    return new PedidoNaoEncontradoException(id);
                });
        pedido.setStatus(EStatusPedido.CANCELADO);
        if (pedido.getAutomovel() != null) {
            pedido.getAutomovel().liberar();
            automovelRepository.save(pedido.getAutomovel());
        }
        Pedido atualizado = pedidoRepository.save(pedido);
        logger.info("Pedido cancelado com sucesso, ID: {}", id);
        return atualizado.toDTO();
    }
}

/**
 * Exceção lançada quando um pedido não é encontrado.
 */
class PedidoNaoEncontradoException extends RuntimeException {
    public PedidoNaoEncontradoException(Long id) {
        super("Pedido não encontrado com id: " + id);
    }
}