package br.unitins.topicos1.service;



import br.unitins.topicos1.dto.CategoriaDTO;
import br.unitins.topicos1.dto.CategoriaResponseDTO;
import br.unitins.topicos1.model.Categoria;
import br.unitins.topicos1.repository.CategoriaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CategoriaServiceImpl implements CategoriaService {


    CategoriaRepository repository;

    @Override
    @Transactional
    public CategoriaResponseDTO insert(CategoriaDTO dto) {
         Categoria novaCategoria = new Categoria();
         novaCategoria.setNomeCategoria(dto.nomeCategoria());
      

        repository.persist(novaCategoria);
        return CategoriaResponseDTO.valueOf(novaCategoria);
    }

    @Override
    public CategoriaResponseDTO update(CategoriaDTO dto, Long id) {
        Categoria CategoriaAtualizada = repository.findById(id);
        if (dto.nomeCategoria() != null){
            CategoriaAtualizada.setNomeCategoria(dto.nomeCategoria());
           }

        repository.persist(CategoriaAtualizada);
        return CategoriaResponseDTO.valueOf(CategoriaAtualizada);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public CategoriaResponseDTO findById(Long id) {
        return CategoriaResponseDTO.valueOf(repository.findById(id));
    }

    @Override
    public List<CategoriaResponseDTO> getAll() {
         List<Categoria> list = repository.findAll().list();
        return list.stream().map(e -> CategoriaResponseDTO.valueOf(e)).collect(Collectors.toList());
    }

  
}
