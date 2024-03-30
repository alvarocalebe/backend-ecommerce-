package br.unitins.topicos1.service;

import br.unitins.topicos1.dto.PedidoDTO;
import br.unitins.topicos1.dto.PedidoResponseDTO;
import br.unitins.topicos1.dto.StatusPedidoDTO;
import br.unitins.topicos1.model.*;
import br.unitins.topicos1.repository.ClienteRepository;
import br.unitins.topicos1.repository.CupomRepository;
import br.unitins.topicos1.repository.PedidoRepository;
import br.unitins.topicos1.repository.BateriaCompletaRepository;
import br.unitins.topicos1.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

 
    @Inject
    PedidoRepository repository;
    @Inject
    ClienteRepository clienteRepository;

    @Inject
    CupomRepository cupomRepository;

    @Inject
    BateriaCompletaRepository produtoRepository;

    private void configurarPedido(PedidoDTO dto, Pedido pedido) {
      
        if (dto.cupom() != null)
            pedido.setCupomPromocional(cupomRepository.findByCodigo(dto.cupom()));

        if (dto.itens() != null) {
            if (pedido.getItensPedido() == null) {
                pedido.setItensPedido(new ArrayList<>());
            } else {
                pedido.getItensPedido().clear();
            }

            pedido.getItensPedido().addAll(dto.itens().stream().map(itemPedidoDTO -> {
                ItemPedido itemPedido = new ItemPedido();
                BateriaCompleta produto = produtoRepository.findById(itemPedidoDTO.idProduto());

                if ((produto.getQuantidadeEstoque() - itemPedido.getQuantidade()) >= 0) {
                    itemPedido.setQuantidade(itemPedidoDTO.quantidade());
                    produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - itemPedido.getQuantidade());
                } else {

                    throw new ValidationException("Estoque", "Quantidade de estoque insuficiente");

                }

                itemPedido.setPrecoUnitario(produto.getPreco());
                itemPedido.setSubTotal(produto.getPreco() * itemPedidoDTO.quantidade());
                itemPedido.setProduto(produto);
                return itemPedido;
            }).toList());
        }
    }

    @Override
    @Transactional
    public PedidoResponseDTO insert(PedidoDTO dto, Long idCliente) {
        Pedido pedido = new Pedido();

        pedido.setCliente(clienteRepository.findById(idCliente));
        pedido.setDataCriacao(LocalDateTime.now());

        configurarPedido(dto, pedido);

        StatusPedido statusPedido = new StatusPedido();

        statusPedido.setData(LocalDateTime.now());

        statusPedido.setStatus(Status.AGUARDANDO_PAGAMENTO);

        pedido.setHistoricoStatus(List.of(statusPedido));

        // salvando pedido
        repository.persist(pedido);

        return PedidoResponseDTO.valueOf(pedido);
    }

    @Override
    @Transactional
    public PedidoResponseDTO update(PedidoDTO dto, Long id) {
        Pedido pedido = repository.findById(id);
        if (pedido != null) {
            configurarPedido(dto, pedido);
            repository.persist(pedido);
            return PedidoResponseDTO.valueOf(pedido);
        }
        return null;
    }

    @Override
    @Transactional
    public PedidoResponseDTO insertStatus(StatusPedidoDTO dto, Long id) {
        Pedido pedido = repository.findById(id);
        pedido.getHistoricoStatus().add(dto.valueOf());
        repository.persist(pedido);
        return PedidoResponseDTO.valueOf(pedido);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public PedidoResponseDTO findById(Long id) {
        return PedidoResponseDTO.valueOf(repository.findById(id));
    }

    @Override
    public List<PedidoResponseDTO> findByClienteId(Long idCliente) {
        return repository.findByClienteId(idCliente).stream().map(PedidoResponseDTO::valueOf).toList();
    }

}
