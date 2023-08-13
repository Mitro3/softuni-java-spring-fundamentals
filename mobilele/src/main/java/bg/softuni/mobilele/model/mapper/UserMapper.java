package bg.softuni.mobilele.model.mapper;

import bg.softuni.mobilele.model.dtos.UserRegisterDTO;
import bg.softuni.mobilele.model.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "active", constant = "true")
    UserEntity userDtoToUserEntity(UserRegisterDTO registerDTO);

}
