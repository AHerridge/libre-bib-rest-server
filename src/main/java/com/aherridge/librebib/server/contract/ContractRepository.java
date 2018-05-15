package com.aherridge.librebib.server.contract;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "contracts", path = "contracts")
public interface ContractRepository extends PagingAndSortingRepository<Contract, String> {

  Iterable<Contract> findByUserId(@Param("userId") String userId);

  Iterable<Contract> findByBookId(@Param("bookId") String bookId);
}
