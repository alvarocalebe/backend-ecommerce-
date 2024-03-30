package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Categoria;

public record CategoriaResponseDTO(
                Long id,
                String nomeCategoria
                 ) {
        public static CategoriaResponseDTO valueOf(Categoria Categoria) {

                return new CategoriaResponseDTO(
                           Categoria.getId(),
                           Categoria.getNomeCategoria());

        }
}