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
}
