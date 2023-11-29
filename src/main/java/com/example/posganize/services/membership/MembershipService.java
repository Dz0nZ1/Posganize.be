package com.example.posganize.services.membership;

import com.example.posganize.models.CreateMembershipModel;
import com.example.posganize.models.MembershipModel;

import java.util.List;
import java.util.Map;

public interface MembershipService {

    List<MembershipModel> getAllMemberships();

    MembershipModel getMembership(Long membershipId);

    List<MembershipModel> getAllMembershipByUserId(Long userId);

    MembershipModel createMembership (CreateMembershipModel membershipModel);

    MembershipModel updateMembership (MembershipModel membershipModel, Long membershipId);

    void deleteMembership(Long membershipId);

    Map<String, Boolean> isActiveMembershipByUserId(Long userId);

}
