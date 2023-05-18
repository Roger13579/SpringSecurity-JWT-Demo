package com.rogerli.springmall.rowMapper;

import com.rogerli.springmall.model.UserView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<UserView> {
    @Override
    public UserView mapRow(ResultSet rs, int i) throws SQLException {
        UserView user = new UserView();
        user.setUserId(rs.getInt("user_id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setCreatedDate(rs.getDate("created_date"));
        user.setLastModifuedDate(rs.getDate("last_modified_date"));
        return user;
    }
}
