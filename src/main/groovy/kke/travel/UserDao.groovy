package kke.travel

import javax.annotation.Resource
import javax.sql.DataSource

/**
 * Created by K.eun on 2014-11-19.
 */
class UserDao {
    @Resource
    DataSource dataSource;

    void add(User user) {
        def conn = dataSource.getConnection()
        def sql = """
INSERT INTO users (email, password,account_name, name, birth_date)
VALUES (?, ?, ?, ?, ?)"""
        def stat = conn.prepareStatement(sql)
        stat.setString(1, user.email)
        stat.setString(2, user.password)
        stat.setString(3,user.accountName)
        stat.setString(4, user.name)
        stat.setDate(5, new java.sql.Date(user.birthDate.time))
        stat.executeUpdate()
        stat.close()
        conn.close()
    }

    List<User> list(int id) {
        def conn = dataSource.getConnection()
        def sql = """
SELECT id, email, password,account_name, name, birth_date
FROM users"""
        def stat = conn.prepareStatement(sql)
        def rs = stat.executeQuery()
        List<User> list = []
        while (rs.next()) {
            def user = new User()
            user.id = rs.getInt("id")
            user.email = rs.getString("email")
            user.password = rs.getString("password")
            user.accountName = rs.getString("account_name")
            user.name = rs.getString("name")
            user.birthDate = rs.getDate("birth_date")
            list.add(user)
        }
        rs.close()
        stat.close()
        conn.close()
        list
    }

    void delete(int id) {
        def conn = dataSource.getConnection()
        def sql = """
DELETE FROM users WHERE id = ?"""
        def stat = conn.prepareStatement(sql)
        stat.setInt(1, id)
        stat.executeUpdate()
        stat.close()
        conn.close()
    }

    void edit(User user) {
        def conn = dataSource.getConnection()
        def sql = """
UPDATE users SET email = ? , password = ? , account_name = ? , name = ? , birth_date = ? ,
WHERE id = ?"""
        def stat = conn.prepareStatement(sql)
        stat.setString(1, user.email)
        stat.setString(2, user.password)
        stat.setString(3,user.accountName)
        stat.setString(4, user.name)
        stat.setDate(5, new java.sql.Date(user.birthDate.time))
        stat.setInt(6, user.id)
        stat.executeUpdate()
        stat.close()
        conn.close()
    }

    User get(int id) {
        def conn = dataSource.getConnection()
        def sql = """
SELECT id, email, password,account_name, name, birth_date FROM users WHERE id = ?"""
        def stat = conn.prepareStatement(sql)
        stat.setInt(1, id)
        def rs = stat.executeQuery()
        rs.next()
        def user = new User()
        user.id = rs.getInt("id")
        user.email = rs.getString("email")
        user.password = rs.getString("password")
        user.accountName = rs.getString("accou")
        user.name = rs.getString("name")
        user.birthDate = rs.getDate("birth_date")
        rs.close()
        stat.close()
        conn.close()
        user
    }
}
