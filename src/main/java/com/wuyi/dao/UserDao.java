package com.wuyi.dao;

import com.wuyi.model.User;
import org.apache.ibatis.annotations.*;

import javax.jws.soap.SOAPBinding;

/**
 * Created by wy on 2017/3/23.
 */
@Mapper
public interface UserDao {
    String TABLE_NAME="user";

    String INSERT_FIELDS=" name, password, salt, head_url ";

    String SELECT_FIELDS=" id, name, password, salt, head_url";

    @Insert({"insert into ", TABLE_NAME ,"(" ,INSERT_FIELDS ,") values (#{name}, #{password}, #{salt}, #{headUrl})"})
    public int addUser(User user);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id = #{id}"})
    public User selectById(int id);

    @Update({"update", TABLE_NAME, "set password = #{password} where id = #{id}"})
    public int updatePassword(User user);

    @Delete({"delete from", TABLE_NAME, "where id = #{id}"})
    public int deleteById(int id);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where name = #{name}"})
    public User selectByName(String name);
}
