package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Insert("INSERT INTO CREDENTIALS (url,username,key,password,userId) VALUES(#{url},#{username},#{key},#{password},#{userId});")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer insertCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET url=#{url},username=#{username}, key=#{key}, password=#{password} where credentialId=#{credentialId};")
    Integer updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS where credentialId=#{credentialId};")
    Integer deleteCredential(Integer credentialId, Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE userId=#{userId};")
    List<Credential> getAllCredentials(Integer userId);
}
