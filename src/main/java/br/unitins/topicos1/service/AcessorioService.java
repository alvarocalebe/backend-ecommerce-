package br.unitins.topicos1.service;

import br.unitins.topicos1.dto.AcessorioDTO;
import br.unitins.topicos1.dto.AcessorioResponseDTO;


import java.util.List;

public interface AcessorioService {
    AcessorioResponseDTO insert(AcessorioDTO dto);

    AcessorioResponseDTO update(AcessorioDTO dto, Long id);

    void delete(Long id);

    void updateNomeImagem(Long id, String nomeImagem) ;

    AcessorioResponseDTO findById(Long id);

    List<AcessorioResponseDTO> getAll();
}
