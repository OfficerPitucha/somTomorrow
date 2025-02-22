package com.example.somtomorrow.dao;

import com.example.somtomorrow.model.Person;
import com.example.somtomorrow.routes.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public enum PersonDao {
    INSTANCE;

    public Person getPersonById(int personId) throws SQLException {
        String sql = "SELECT * FROM somtomorrow.person WHERE person_id = ?";
        Person person = null;

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, personId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                person = new Person();
                person.setPerson_id(rs.getInt("person_id"));
                person.setName(rs.getString("name"));
                person.setSurname(rs.getString("surname"));
                person.setEmail(rs.getString("email"));
            }
        }
        return person;
    }

    public boolean updatePerson(Person person) throws SQLException {
        String sql = "UPDATE somtomorrow.person SET name = ?, surname = ?, email = ? WHERE person_id = ?";

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, person.getName());
            ps.setString(2, person.getSurname());
            ps.setString(3, person.getEmail());
            ps.setInt(4, person.getPerson_id());

            return ps.executeUpdate() > 0;
        }
    }
}
