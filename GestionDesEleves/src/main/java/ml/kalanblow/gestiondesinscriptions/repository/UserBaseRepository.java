package ml.kalanblow.gestiondesinscriptions.repository;


import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

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
