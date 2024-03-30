package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Endereco;
import br.unitins.topicos1.model.Estado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoDTO(
        @NotBlank
        String rua,
        @NotNull
        Integer numero,
        @NotBlank
        String cidade,
        @NotBlank
        String cep,
        @NotNull
        Estado estado
) {

    public Endereco valueOf() {
        Endereco novo = new Endereco();
        novo.setCep(cep());
        novo.setCidade(cidade());
        novo.setEstado(estado());
        novo.setNumero(numero());
        novo.setRua(rua());
        return novo;
    }
}
