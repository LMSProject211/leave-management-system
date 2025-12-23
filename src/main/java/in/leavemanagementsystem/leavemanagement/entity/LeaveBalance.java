package in.leavemanagementsystem.leavemanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "leave_balance")
public class LeaveBalance {

    @Id
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int totalLeaves;
    private int remainingLeaves;

    // getters & setters
}
