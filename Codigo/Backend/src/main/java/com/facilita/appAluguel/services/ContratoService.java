package com.facilita.appAluguel.services;

import com.facilita.appAluguel.dto.ContratoCreateDTO;
import com.facilita.appAluguel.dto.ContratoDTO;
import com.facilita.appAluguel.dto.ContratoUpdateDTO;
import com.facilita.appAluguel.models.Automovel;
import com.facilita.appAluguel.models.Contrato;
import com.facilita.appAluguel.models.Empresa;
import com.facilita.appAluguel.models.Pedido;
import com.facilita.appAluguel.repositories.AutomovelRepository;
import com.facilita.appAluguel.repositories.ContratoRepository;
import com.facilita.appAluguel.repositories.EmpresaRepository;
import com.facilita.appAluguel.repositories.PedidoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Serviço para gerenciamento de contratos.
 */
@Service
public class ContratoService {

    private static final Logger logger = LoggerFactory.getLogger(ContratoService.class);

    @Autowired
    private final ContratoRepository contratoRepository;

    @Autowired
    private final PedidoRepository pedidoRepository;

    @Autowired
    private final AutomovelRepository automovelRepository;

    @Autowired
    private final EmpresaRepository empresaRepository;

    public ContratoService(ContratoRepository contratoRepository,
                           PedidoRepository pedidoRepository,
                           AutomovelRepository automovelRepository,
                           EmpresaRepository empresaRepository) {
        this.contratoRepository = contratoRepository;
        this.pedidoRepository = pedidoRepository;
        this.automovelRepository = automovelRepository;
        this.empresaRepository = empresaRepository;
    }

    /**
     * Lista todos os contratos cadastrados.
     * @return Lista de contratos.
     */
    public List<Contrato> findAll() {
        logger.info("Listando todos os contratos");
        return contratoRepository.findAll();
    }

    /**
     * Busca um contrato pelo ID.
     * @param id ID do contrato.
     * @return Optional contendo o contrato, se encontrado.
     */
    public Contrato findById(Long id) {
        return contratoRepository.findById(id).orElse(null);

    }

    /**
     * Cria e associa um contrato a um pedido.
     * @param pedidoId ID do pedido associado.
     * @param contratoCreateDTO DTO com dados do contrato (ex.: empresaId).
     * @return ContratoDTO do contrato criado.
     * @throws IllegalArgumentException se o pedido ou empresa não existirem, ou se o automóvel estiver indisponível.
     */
    @Transactional
    public ContratoDTO criarContrato(Long pedidoId, @Valid ContratoCreateDTO contratoCreateDTO) {
        logger.info("Criando contrato para pedido ID: {}", pedidoId);
        Objects.requireNonNull(contratoCreateDTO, "DTO de criação não pode ser nulo");

        // Buscar pedido
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> {
                    logger.error("Pedido não encontrado com ID: {}", pedidoId);
                    return new IllegalArgumentException("Pedido não encontrado com ID: " + pedidoId);
                });

        // Buscar empresa
        Empresa empresa = empresaRepository.findById(contratoCreateDTO.empresaId())
                .orElseThrow(() -> {
                    logger.error("Empresa não encontrada com ID: {}", contratoCreateDTO.empresaId());
                    return new IllegalArgumentException("Empresa não encontrada com ID: " + contratoCreateDTO.empresaId());
                });

        // Verificar automóvel
        Automovel automovel = pedido.getAutomovel();
        if (automovel == null || !automovel.reservar(pedido)) {
            logger.error("Automóvel indisponível para o pedido ID: {}", pedidoId);
            throw new IllegalArgumentException("Automóvel indisponível para o pedido");
        }

        // Criar contrato
        Contrato contrato = new Contrato();
        contrato.setEmpresa(empresa);
        contrato.setPedido(pedido);
        contrato.setDataAssinatura(LocalDate.now());
        pedido.setContrato(contrato);

        // Salvar entidades
        automovelRepository.save(automovel);
        pedidoRepository.save(pedido);
        Contrato salvo = contratoRepository.save(contrato);

        logger.info("Contrato criado com sucesso, ID: {}", salvo.getId());
        return salvo.toDTO();
    }

    /**
     * Atualiza um contrato existente com base no DTO fornecido.
     * @param id ID do contrato a ser atualizado.
     * @param contratoUpdateDTO DTO com os dados de atualização.
     * @return ContratoDTO do contrato atualizado.
     * @throws ContratoNaoEncontradoException se o contrato não for encontrado.
     * @throws IllegalArgumentException se o empresaId for inválido.
     */
    @Transactional
    public ContratoDTO atualizarContrato(Long id, @Valid ContratoUpdateDTO contratoUpdateDTO) {
        logger.info("Atualizando contrato com ID: {}", id);
        Objects.requireNonNull(contratoUpdateDTO, "DTO de atualização não pode ser nulo");

        Contrato contrato = contratoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Contrato não encontrado com ID: {}", id);
                    return new ContratoNaoEncontradoException(id);
                });

        if (contratoUpdateDTO.empresaId() != null) {
            Empresa empresa = empresaRepository.findById(contratoUpdateDTO.empresaId())
                    .orElseThrow(() -> {
                        logger.error("Empresa não encontrada com ID: {}", contratoUpdateDTO.empresaId());
                        return new IllegalArgumentException("Empresa não encontrada com ID: " + contratoUpdateDTO.empresaId());
                    });
            contrato.setEmpresa(empresa);
        }

        Contrato atualizado = contratoRepository.save(contrato);
        logger.info("Contrato atualizado com sucesso, ID: {}", id);
        return atualizado.toDTO();
    }

    /**
     * Encerra um contrato, liberando o automóvel associado.
     * @param id ID do contrato a ser encerrado.
     * @return ContratoDTO do contrato encerrado.
     * @throws ContratoNaoEncontradoException se o contrato não for encontrado.
     */
    @Transactional
    public ContratoDTO encerrarContrato(Long id) {
        logger.info("Encerrando contrato com ID: {}", id);
        Contrato contrato = contratoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Contrato não encontrado com ID: {}", id);
                    return new ContratoNaoEncontradoException(id);
                });

        Pedido pedido = contrato.getPedido();
        if (pedido != null && pedido.getAutomovel() != null) {
            pedido.getAutomovel().liberar();
            automovelRepository.save(pedido.getAutomovel());
        }

        contrato.encerrarContrato();
        Contrato atualizado = contratoRepository.save(contrato);
        logger.info("Contrato encerrado com sucesso, ID: {}", id);
        return atualizado.toDTO();
    }

    /**
     * Deleta um contrato pelo ID.
     * @param id ID do contrato a ser deletado.
     * @throws ContratoNaoEncontradoException se o contrato não for encontrado.
     */
    @Transactional
    public void delete(Long id) {
        logger.info("Deletando contrato com ID: {}", id);
        if (!contratoRepository.existsById(id)) {
            logger.error("Contrato não encontrado com ID: {}", id);
            throw new ContratoNaoEncontradoException(id);
        }
        contratoRepository.deleteById(id);
        logger.info("Contrato deletado com sucesso, ID: {}", id);
    }
}

/**
 * Exceção lançada quando um contrato não é encontrado.
 */
class ContratoNaoEncontradoException extends RuntimeException {
    public ContratoNaoEncontradoException(Long id) {
        super("Contrato não encontrado com id: " + id);
    }
}