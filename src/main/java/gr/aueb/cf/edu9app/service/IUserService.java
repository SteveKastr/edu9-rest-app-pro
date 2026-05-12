package gr.aueb.cf.edu9app.service;

import gr.aueb.cf.edu9app.core.exception.EntityAlreadyExistsException;
import gr.aueb.cf.edu9app.core.exception.EntityInvalidArgumentException;
import gr.aueb.cf.edu9app.core.exception.EntityNotFoundException;
import gr.aueb.cf.edu9app.dto.UserInsertDTO;
import gr.aueb.cf.edu9app.dto.UserReadOnlyDTO;

import java.util.UUID;

public interface IUserService {
    UserReadOnlyDTO saveUser(UserInsertDTO userInsertDTO) throws EntityAlreadyExistsException,
            EntityInvalidArgumentException;

    UserReadOnlyDTO getUserByUUID(UUID uuid) throws EntityNotFoundException;
    UserReadOnlyDTO getUserByUUIDDeletedFalse(UUID uuid) throws EntityNotFoundException;
}
