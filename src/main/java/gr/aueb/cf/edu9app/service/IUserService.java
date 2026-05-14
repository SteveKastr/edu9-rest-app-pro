package gr.aueb.cf.edu9app.service;

import gr.aueb.cf.edu9app.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.edu9app.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.edu9app.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.edu9app.dto.UserInsertDTO;
import gr.aueb.cf.edu9app.dto.UserReadOnlyDTO;

import java.util.UUID;

public interface IUserService {
    UserReadOnlyDTO saveUser(UserInsertDTO userInsertDTO) throws EntityAlreadyExistsException,
            EntityInvalidArgumentException;

    UserReadOnlyDTO getUserByUUID(UUID uuid) throws EntityNotFoundException;
    UserReadOnlyDTO getUserByUUIDDeletedFalse(UUID uuid) throws EntityNotFoundException;
}
