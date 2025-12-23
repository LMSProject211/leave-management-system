package in.leavemanagementsystem.leavemanagement.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import in.leavemanagementsystem.leavemanagement.entity.LeaveBalance;
import in.leavemanagementsystem.leavemanagement.entity.User;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, User> {

    LeaveBalance findByUser(User user);
}
