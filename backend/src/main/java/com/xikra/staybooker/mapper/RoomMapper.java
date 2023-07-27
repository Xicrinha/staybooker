package com.xikra.staybooker.mapper;

import com.xikra.staybooker.domain.Room;
import com.xikra.staybooker.model.RoomDTO;
import org.mapstruct.Mapper;

@Mapper
public interface RoomMapper {

    RoomDTO toDTO(Room room);

    Room toEntity(RoomDTO roomDTO);
}
