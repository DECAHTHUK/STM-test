package com.example.repository;

import com.example.models.user.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.example.models.Id;

import java.util.UUID;

@Mapper
public interface UserMapper {

    @Result(property = "id", column = "id")
    @Select("""
            INSERT INTO users (email, password, full_name, user_role)
            VALUES(#{email}, #{password}, #{fullName}, #{userRole})
            RETURNING id;
            """)
    Id insertUser(User user) throws RuntimeException;

    @Results(value = {
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "userRole", column = "user_role"),
            @Result(column = "id", property = "id")
    })
    @Select("""
            SELECT u.id, u.email, u.password, u.full_name, u.user_role
            FROM users as u
            WHERE u.id = '${uuid}';
            """)
    User selectUser(@Param("uuid") UUID uuid);

    @Results(value = {
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "userRole", column = "user_role")
    })
    @Select("""
            SELECT u.id, u.email, u.password, u.full_name, u.user_role
            FROM users as u
            WHERE u.email = #{email};
            """)
    User selectUserByEmail(String email);

    @Update("""
            UPDATE users
            SET email=#{email}, password=#{password}, full_name=#{fullName}, user_role=#{userRole}
            WHERE id = uuid(#{id});
            """)
    void updateUser(User user);

    @Delete("""
            DELETE FROM users
            WHERE id = '${userId}';
            """)
    void deleteUser(@Param("userId") UUID userId);

    @Select("""
            SELECT refresh_token FROM users
            WHERE email = #{email};
            """)
    String selectRefreshToken(String email);

    @Update("""
            UPDATE users
            SET refresh_token = #{refreshToken}
            WHERE email = #{email};
            """)
    void updateRefreshToken(String email, String refreshToken);
}
