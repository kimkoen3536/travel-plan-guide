package kke.travel

import org.springframework.stereotype.Repository

import javax.annotation.Resource
import javax.sql.DataSource

@Repository
class PlanDao {
    @Resource
    DataSource dataSource;

    void add(Plan plan) {
        def conn = dataSource.getConnection()
        def sql = """
INSERT INTO plans (user_id, account_name, title, location, start_date, end_date, is_public, num_likes)
VALUES (?,?,?, ?, ?, ?, ?, ?)"""
        def stat = conn.prepareStatement(sql)
        stat.setInt(1,plan.user_id)
        stat.setString(2,plan.accountName)
        stat.setString(3, plan.title)
        stat.setString(4, plan.location)
        stat.setDate(5, new java.sql.Date(plan.startDate.time))
        stat.setDate(6, new java.sql.Date(plan.endDate.time))
        stat.setBoolean(7, plan.public_)
        stat.setInt(8, plan.numLikes)
        stat.executeUpdate()
        stat.close()
        conn.close()
    }

    List<Plan> list(int user_id) {
        def conn = dataSource.getConnection()
        def sql = """
SELECT id, title, location, start_date, end_date, is_public, num_likes
FROM plans where user_id = ?"""
        def stat = conn.prepareStatement(sql)
        stat.setInt(1, user_id)
        def rs = stat.executeQuery()
        List<Plan> list = []
        while (rs.next()) {
            def plan = new Plan()
            plan.id = rs.getInt("id")
            plan.title = rs.getString("title")
            plan.location = rs.getString("location")
            plan.startDate = rs.getDate("start_date")
            plan.endDate = rs.getDate("end_date")
            plan.public_ = rs.getBoolean("is_public")
            plan.numLikes = rs.getInt("num_likes")
            list.add(plan)
        }
        rs.close()
        stat.close()
        conn.close()
        list
    }

    List<Plan> list2(int id) {
        def conn = dataSource.getConnection()
        def sql = """
SELECT id, user_id, title, location, start_date, end_date, is_public, account_name, num_likes
FROM plans """
        def stat = conn.prepareStatement(sql)
        def rs = stat.executeQuery()
        List<Plan> list2 = []
        while (rs.next()) {
            def plan = new Plan()
            plan.id = rs.getInt("id")
            plan.user_id = rs.getInt("user_id");
            plan.title = rs.getString("title")
            plan.location = rs.getString("location")
            plan.startDate = rs.getDate("start_date")
            plan.endDate = rs.getDate("end_date")
            plan.public_ = rs.getBoolean("is_public")
            plan.accountName = rs.getString("account_name")
            plan.numLikes = rs.getInt("num_likes")
            list2.add(plan)
        }
        rs.close()
        stat.close()
        conn.close()
        list2
    }

    void delete(int id) {
        def conn = dataSource.getConnection()
        def sql = """
DELETE FROM plans WHERE id = ?"""
        def stat = conn.prepareStatement(sql)
        stat.setInt(1, id)
        stat.executeUpdate()
        stat.close()
        conn.close()
    }

    void edit(Plan plan) {
        def conn = dataSource.getConnection()
        def sql = """
UPDATE plans SET title = ? , location = ? , start_date = ? , end_date = ? ,
is_public = ? , num_likes = ? WHERE id = ?"""
        def stat = conn.prepareStatement(sql)
        stat.setString(1, plan.title)
        stat.setString(2, plan.location)
        stat.setDate(3, new java.sql.Date(plan.startDate.time))
        stat.setDate(4, new java.sql.Date(plan.endDate.time))
        stat.setBoolean(5, plan.isPublic_())
        stat.setInt(6, plan.numLikes)
        stat.setInt(7, plan.id)
        stat.executeUpdate()
        stat.close()
        conn.close()
    }

    Plan get(int id) {
        def conn = dataSource.getConnection()
        def sql = """
SELECT id, title, location, start_date, end_date, is_public, num_likes FROM plans WHERE id = ?"""
        def stat = conn.prepareStatement(sql)
        stat.setInt(1, id)
        def rs = stat.executeQuery()
        rs.next()
        def plan = new Plan()
        plan.id = rs.getInt("id")
        plan.title = rs.getString("title")
        plan.location = rs.getString("location")
        plan.startDate = rs.getDate("start_date")
        plan.endDate = rs.getDate("end_date")
        plan.public_ = rs.getBoolean("is_public")
        plan.numLikes = rs.getInt("num_likes")
        rs.close()
        stat.close()
        conn.close()
        plan
    }
}
