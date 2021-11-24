package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {
    @Insert("INSERT INTO CREDENTIALS(url, username, key, password, userid)" +
            "VALUES (#{url}, #{username}, #{key}, #{password}, #{userid});")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insertCredentials(Credentials credentials);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List <Credentials> selectCredentials(int userid);

    @Update("UPDATE CREDENTIALS" +
            "SET url = #{url}, username = #{username}, key = #{key}, password = #{password}" +
            "WHERE credentialid = #{credentialid};")
    int updateCredentials(Credentials credentials);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid} AND userid = #{userid}")
    int deleteCredentials(int credentialid, int userid);
}
