package org.example.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.models.Id;
import org.example.models.transporter.TransporterDto;
import org.example.models.transporter.TransporterEntity;

import java.util.UUID;

@Mapper
public interface TransporterMapper {
    @Result(property = "id", column = "id")
    @Select("""
            INSERT INTO transporters (name, phone_number)
            VALUES(#{name}, #{phoneNumber})
            RETURNING id;
            """)
    Id insertTransporter(TransporterDto transporterDto);

    @Result(property = "phoneNumber", column = "phone_number")
    @Select("""
            SELECT * FROM transporters
            WHERE id = '${uuid}';
            """)
    TransporterEntity selectTransporter(@Param("uuid") UUID uuid);

    @Update("""
            UPDATE transporters
            SET name = #{name}, phone_number = #{phoneNumber}
            WHERE id = uuid(#{id})
            """)
    void updateTransporter(TransporterEntity transporter);

    @Delete("""
            DELETE FROM transporters
            WHERE id = '${uuid}';
            """)
    void deleteTransporter(@Param("uuid") UUID uuid);
}
