package com.example.posganize.mappers;
import com.example.posganize.entities.Membership;
import com.example.posganize.models.CreateMembershipModel;
import com.example.posganize.models.MembershipModel;
import com.example.posganize.models.UserMembershipModel;
import org.springframework.data.domain.Page;


import java.util.ArrayList;
import java.util.List;

public class MembershipMapper {


    public static MembershipModel mapMembershipToMembershipModel(Membership membership) {
        var model =  MembershipModel.builder()
                .id(membership.getMembership_id())
                .user(UserMembershipModel.builder()
                        .userId(membership.getUser().getUser_id())
                        .firstName(membership.getUser().getFirstName())
                        .lastName(membership.getUser().getLastName())
                        .build())
                .startDate(membership.getStartDate())
                .expireDate(membership.getExpireDate())
                .price(membership.getPrice())
                .active(membership.getActive())
                .build();
        if(membership.getTrainings() != null) model.setTrainings((TrainingMapper.mapTrainingSetToTrainingModelSet(membership.getTrainings())));
        else model.setTrainings(null);
        return model;
    }


    public static Membership mapMembershipModelToMembership(MembershipModel membership) {
        var entity = Membership.builder()
                .membership_id(membership.getId())
                .startDate(membership.getStartDate())
                .expireDate(membership.getExpireDate())
                .price(membership.getPrice())
                .active(membership.getActive())
                .build();
        if(membership.getTrainings() != null) entity.setTrainings((TrainingMapper.mapTrainingModelSetToTrainingSet(membership.getTrainings())));
        else entity.setTrainings(null);
        return entity;
    }

    public static Membership mapCreateMembershipModelToMembership(CreateMembershipModel membership) {
        return Membership.builder()
//                .trainings(membership.getTrainings())
                .startDate(membership.getStartDate())
                .expireDate(membership.getExpireDate())
                .build();
    }


    public static List<MembershipModel> mapMembershipListToMembershipModelList(List<Membership> memberships) {
        List<MembershipModel> modelList = new ArrayList<>();
        for (Membership entity : memberships) {
            modelList.add(mapMembershipToMembershipModel(entity));
        }
        return modelList;

    }


    public static List<MembershipModel> mapMembershipPageableToMembershipModel (Page<Membership> memberships) {
        List<MembershipModel> modelList = new ArrayList<>();
        for (Membership entity : memberships) {
            modelList.add(mapMembershipToMembershipModel(entity));
        }
        return modelList;
    }

}
