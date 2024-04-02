package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.BaquetaDTO;
import br.unitins.topicos1.dto.BaquetaResponseDTO;


public interface BaquetaService {
   
    BaquetaResponseDTO insert(BaquetaDTO dto);
    BaquetaResponseDTO update(BaquetaDTO dto, Long idMarca);
    BaquetaResponseDTO findById(Long idMarca);
    void delete(Long id);

    List<BaquetaResponseDTO> getAll();
    
}