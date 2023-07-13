package com.xikra.staybooker.mapper;

import com.xikra.staybooker.domain.Room;
import com.xikra.staybooker.model.RoomDTO;
import org.mapstruct.Mapper;

@Mapper
public interface RoomMapper {

    RoomDTO roomToRoomDTO(Room room);

    Room roomDTOToRoom(RoomDTO roomDTO);
}
