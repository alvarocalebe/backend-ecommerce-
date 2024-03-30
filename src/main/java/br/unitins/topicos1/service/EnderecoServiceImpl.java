package br.unitins.topicos1.service;

import br.unitins.topicos1.dto.EnderecoDTO;
import br.unitins.topicos1.dto.EnderecoResponseDTO;
import br.unitins.topicos1.model.Cliente;
import br.unitins.topicos1.model.Endereco;
import br.unitins.topicos1.repository.ClienteRepository;
import br.unitins.topicos1.repository.EnderecoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService {

    @Inject
    EnderecoRepository repository;

    @Inject
    ClienteRepository clienteRepository;

    @Override
    @Transactional
    public EnderecoResponseDTO insert(EnderecoDTO dto, Long idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente);
        Endereco endereco = dto.valueOf();
        if (cliente != null) {
            cliente.getEndereco().add(endereco);
            clienteRepository.persistAndFlush(cliente); // se não fizer isso não atualiza o id no objeto adicionado
        }
        return EnderecoResponseDTO.valueOf(endereco);
    }

    @Override
    @Transactional
    public EnderecoResponseDTO update(EnderecoDTO dto, Long id) {
        Endereco endereco = repository.findById(id);
        if (dto.rua() != null) endereco.setRua(dto.rua());
        if (dto.numero() != null) endereco.setNumero(dto.numero());
        if (dto.estado() != null) endereco.setEstado(dto.estado());
        if (dto.cidade() != null) endereco.setCidade(dto.cidade());
        if (dto.cep() != null) endereco.setCep(dto.cep());
        repository.persist(endereco);
        return EnderecoResponseDTO.valueOf(endereco);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    public EnderecoResponseDTO findById(Long id) {
        return EnderecoResponseDTO.valueOf(repository.findById(id));
    }

    @Override
    public List<EnderecoResponseDTO> findByCep(String cep) {
        return repository.findByCep(cep).stream().map(EnderecoResponseDTO::valueOf).toList();
    }
}
