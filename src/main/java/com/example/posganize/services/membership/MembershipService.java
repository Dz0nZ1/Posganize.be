package com.example.posganize.services.membership;

import com.example.posganize.models.CreateMembershipModel;
import com.example.posganize.models.MembershipModel;
import com.example.posganize.models.MembershipPageableModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface MembershipService {

    List<MembershipModel> getAllMemberships();

    MembershipModel getMembership(Long membershipId);

    MembershipPageableModel getAllMembershipsByUserId(Long userId, int pageNumber, int pageSize, boolean ascending);

    Map<String, Object> getRevenueAndMembers(LocalDate fromDate , LocalDate toDate);

    MembershipModel createMembership (CreateMembershipModel membershipModel);

    MembershipModel updateMembership (MembershipModel membershipModel, Long membershipId);

    void deleteMembership(Long membershipId);
    void checkAndUpdateMembershipStatus();

    Map<String, Boolean> isActiveMembershipByUserId(Long userId);

}
