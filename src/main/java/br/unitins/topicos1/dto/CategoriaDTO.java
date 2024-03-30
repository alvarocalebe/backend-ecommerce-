package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Categoria;
import jakarta.validation.constraints.NotBlank;


public record CategoriaDTO(
     @NotBlank
    String nomeCategoria
   

) {

    public Categoria valueOf() {
        Categoria categoria = new Categoria();
        categoria.setNomeCategoria(nomeCategoria());
        
        return categoria;
    }
   
}