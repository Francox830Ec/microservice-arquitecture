package org.service.account_movement.account.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.service.account_movement.account.domain.model.AccountDTO;
import org.service.account_movement.account.domain.model.AccountDTOReadingDB;
import org.service.account_movement.account.infrastructure.entity.AccountEntity;
import org.service.account_movement.account.infrastructure.hash.AccountHash;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IAccountMapper {
    AccountDTO entityToDTO(AccountEntity accountEntity);
    AccountEntity dtoToEntity(AccountDTO accountDTO);
    AccountHash dtoToHash(AccountDTO accountDTO);
    List<AccountDTO> iterableToList(Iterable<AccountHash> accountHashes);
    AccountHash dtoReadingDBToHash(AccountDTOReadingDB accountDTOReadingDB);
    AccountDTO hashToDTO(AccountHash accountHash);

}
