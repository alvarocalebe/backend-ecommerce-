package br.unitins.topicos1.dto;


import br.unitins.topicos1.model.TipoPagamento;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record PedidoDTO(
        @NotBlank TipoPagamento tipoPagamento,
        List<ItemPedidoDTO> itens,
        String cupom
) {

}
