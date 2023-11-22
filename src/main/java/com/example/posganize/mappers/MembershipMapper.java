package com.example.posganize.mappers;
import com.example.posganize.entities.Membership;
import com.example.posganize.models.CreateMembershipModel;
import com.example.posganize.models.MembershipModel;
import com.example.posganize.models.UserMembershipModel;


import java.util.ArrayList;
import java.util.List;

public class MembershipMapper {


    public static MembershipModel mapMembershipToMembershipModel(Membership membership) {
        return MembershipModel.builder()
                .user(UserMembershipModel.builder()
                        .userId(membership.getUser().getUser_id())
                        .firstName(membership.getUser().getFirstName())
                        .lastName(membership.getUser().getLastName())
                        .build())
                .trainings(TrainingMapper.mapTrainingListToTrainingModelList(membership.getTrainings()))
                .startDate(membership.getStartDate())
                .expireDate(membership.getExpireDate())
                .price(membership.getPrice())
                .active(membership.getActive())
                .build();
    }


    public static Membership mapMembershipModelToMembership(MembershipModel membership) {
        return Membership.builder()
//                .user(membership.getUser())
                .trainings(TrainingMapper.mapTrainingModelListToTrainingList(membership.getTrainings()))
                .startDate(membership.getStartDate())
                .expireDate(membership.getExpireDate())
                .price(membership.getPrice())
                .active(membership.getActive())
                .build();
    }

    public static Membership mapCreateMembershipModelToMembership(CreateMembershipModel membership) {
        return Membership.builder()
//                .trainings(membership.getTrainings())
                .startDate(membership.getStartDate())
                .expireDate(membership.getExpireDate())
                .price(membership.getPrice())
                .active(membership.getActive())
                .build();
    }


    public static List<MembershipModel> mapMembershipListToMembershipModelList(List<Membership> memberships) {
        List<MembershipModel> modelList = new ArrayList<>();
        for (Membership entity : memberships) {
            modelList.add(mapMembershipToMembershipModel(entity));
        }
        return modelList;

    }

}
