package com.gym.repository;

import com.gym.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // Custom query to find attendance by date part of checkInTime could be useful
    // For simple "today" count, we might default to findAll() filtered or custom
    // query.
    // Let's add a custom query for counting by date if needed later, but for now
    // standard methods + logic in service or query here.

    // Example: count attendance for today.
    // Since checkInTime is LocalDateTime, we need ranges.
    // Or we can just use native query "SELECT COUNT(*) FROM attendance WHERE
    // DATE(check_in_time) = CURDATE()"
    // But let's stick to standard JPA for now or add native query if the
    // requirement explicitly asks for SQL.
    // Requirement says: SELECT COUNT(*) FROM attendance WHERE date = TODAY

    @org.springframework.data.jpa.repository.Query(value = "SELECT COUNT(*) FROM attendance WHERE DATE(check_in_time) = CURRENT_DATE", nativeQuery = true)
    long countTodayAttendance();
}
