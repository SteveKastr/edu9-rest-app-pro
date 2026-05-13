package gr.aueb.cf.edu9app.service;

import gr.aueb.cf.edu9app.core.exception.EntityAlreadyExistsException;
import gr.aueb.cf.edu9app.core.exception.EntityInvalidArgumentException;
import gr.aueb.cf.edu9app.core.exception.EntityNotFoundException;
import gr.aueb.cf.edu9app.dto.UserInsertDTO;
import gr.aueb.cf.edu9app.dto.UserReadOnlyDTO;
import gr.aueb.cf.edu9app.mapper.Mapper;
import gr.aueb.cf.edu9app.model.Role;
import gr.aueb.cf.edu9app.model.User;
import gr.aueb.cf.edu9app.repository.RoleRepository;
import gr.aueb.cf.edu9app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final Mapper mapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = {EntityAlreadyExistsException.class, EntityInvalidArgumentException.class})
    public UserReadOnlyDTO saveUser(UserInsertDTO userInsertDTO) throws EntityAlreadyExistsException, EntityInvalidArgumentException {
        try {
            if (userRepository.findByUsername(userInsertDTO.username()).isPresent()) {
                throw new EntityAlreadyExistsException("User", "User with username=" +userInsertDTO.username() + "already exists.");
            }
            User user = mapper.mapToUserEntity(userInsertDTO);
            user.setPassword(passwordEncoder.encode(userInsertDTO.password()));
            Role role = roleRepository.findById(userInsertDTO.roleId())
                    .orElseThrow(() -> new EntityInvalidArgumentException("Role", "Role with id=" + userInsertDTO.roleId() + "doesn't exists."));
            role.addUser(user);
            userRepository.save(user);
            log.info("User with username={} saved successfully", userInsertDTO.username());
            return mapper.mapToUserReadOnlyDTO(user);


        } catch (EntityAlreadyExistsException e) {
            log.error("Save failed. User with username={} already exists", userInsertDTO.username());
            throw e;
        } catch (EntityInvalidArgumentException e) {
            log.error("Saved failed. Invalid arguments for user with username={}", userInsertDTO.username());
            throw e;
        }
    }

    @Override
    @PreAuthorize("hasAuthority('VIEW_USER')")
    @Transactional(readOnly = true)
    public UserReadOnlyDTO getUserByUUID(UUID uuid) throws EntityNotFoundException {
        try {
            User user = userRepository.findByUuid(uuid)
                    .orElseThrow(() -> new EntityNotFoundException("User", "User with uuid=" + uuid + " not found"));

            log.debug("User with uuid={} found successfully", uuid);
            return mapper.mapToUserReadOnlyDTO(user);

        } catch (EntityNotFoundException e) {
            log.error("Get failed. User with uuid={} not found", uuid);
            throw e;
        }
    }

    @Override
    @PreAuthorize("hasAuthority('VIEW_USER')")
    @Transactional(readOnly = true)
    public UserReadOnlyDTO getUserByUUIDDeletedFalse(UUID uuid) throws EntityNotFoundException {
        try {
            User user = userRepository.findByUuidAndDeletedFalse(uuid)
                    .orElseThrow(() -> new EntityNotFoundException("User", "User with uuid=" + uuid + " not found"));

            log.debug("Active user with uuid={} found successfully", uuid);
            return mapper.mapToUserReadOnlyDTO(user);

        } catch (EntityNotFoundException e) {
            log.error("Get failed. Active user with uuid={} not found", uuid);
            throw e;
        }
    }
}
