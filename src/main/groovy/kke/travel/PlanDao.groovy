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
INSERT INTO plans (title, location, start_date, end_date, is_public, num_likes)
VALUES (?, ?, ?, ?, ?, ?)"""
        def stat = conn.prepareStatement(sql)
        stat.setString(1, plan.title)
        stat.setString(2, plan.location)
        stat.setDate(3, new java.sql.Date(plan.startDate.time))
        stat.setDate(4, new java.sql.Date(plan.endDate.time))
        stat.setBoolean(5, plan.public_)
        stat.setInt(6, plan.numLikes)
        stat.executeUpdate()
    }

    List<Plan> list(int id) {
        def conn = dataSource.getConnection()
        def sql = """
SELECT id, title, location, start_date, end_date, is_public, num_likes
FROM plans"""
        def stat = conn.prepareStatement(sql)
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
        list
    }
}