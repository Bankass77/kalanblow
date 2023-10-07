package ml.kalanblow.gestiondesinscriptions.repository;


import ml.kalanblow.gestiondesinscriptions.model.UserName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface UserBaseRepository<T> extends Repository<T, Long>{
    <S extends T> S save(S entity);
    <S extends T> List<S> saveAll(Iterable<S> entities);

    Optional<T> findById(Long id);
    Iterable<T> findAllById(Iterable<Long> ids);

    void deleteById(Long id);
    void delete(T entity);
    void deleteAllById(Iterable<? extends Long> ids);
    void deleteAll(Iterable<? extends T> entities);

}
