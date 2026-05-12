package gr.aueb.cf.edu9app.mapper;

import gr.aueb.cf.edu9app.dto.UserInsertDTO;
import gr.aueb.cf.edu9app.dto.UserReadOnlyDTO;
import gr.aueb.cf.edu9app.model.User;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public User mapToUserEntity(UserInsertDTO insertDTO) {
        return new User(insertDTO.username(), insertDTO.password());
    }

    public UserReadOnlyDTO mapToUserReadOnlyDTO(User user) {
        return new UserReadOnlyDTO(user.getUuid().toString(), user.getUsername(),
                user.getRole().getName());
    }
}
