package com.aherridge.librebib.server.contract;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
@RepositoryRestResource(collectionResourceRel = "contracts", path = "contracts")
public interface ContractRepository extends PagingAndSortingRepository<Contract, String> {

  Collection<Contract> findAllByUserId(@Param("userId") String userId);

  Collection<Contract> findAllByBookId(@Param("bookId") String bookId);
}
