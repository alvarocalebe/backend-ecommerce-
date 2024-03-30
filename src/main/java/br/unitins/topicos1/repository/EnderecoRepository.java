package br.unitins.topicos1.repository;

import br.unitins.topicos1.model.Endereco;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco> {

    public List<Endereco> findByCep(String cep) {
        return find("cep = ?1", cep).list();
    }

    public boolean delete(Long id) {
        try {
            //desfazer as relações antes de deletar
            getEntityManager().createNativeQuery(
                    "delete from cliente_endereco where id_endereco = " + id
            ).executeUpdate();


            return deleteById(id);
        } catch (Exception e) {
            return false;
        }
    }
}
