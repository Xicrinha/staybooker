package com.xikra.staybooker.repository;

import com.xikra.staybooker.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Long> {
}
