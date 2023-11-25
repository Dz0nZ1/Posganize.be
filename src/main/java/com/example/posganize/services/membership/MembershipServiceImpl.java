package com.example.posganize.services.membership;


import com.example.posganize.exceptions.MembershipNotFoundException;
import com.example.posganize.exceptions.UserNotFoundException;
import com.example.posganize.mappers.MembershipMapper;
import com.example.posganize.models.CreateMembershipModel;
import com.example.posganize.models.MembershipModel;
import com.example.posganize.repository.MembershipRepository;
import com.example.posganize.repository.UsersRepository;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;

    private final UsersRepository usersRepository;



    public MembershipServiceImpl(MembershipRepository membershipRepository, UsersRepository usersRepository) {
        this.membershipRepository = membershipRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public List<MembershipModel> getAllMemberships() {
        return MembershipMapper.mapMembershipListToMembershipModelList(membershipRepository.findAll());
    }

    @Override
    public MembershipModel getMembership(Long membershipId) {
        return MembershipMapper.mapMembershipToMembershipModel(membershipRepository.findById(membershipId).orElseThrow(() -> new NullPointerException("Membership not found")));
    }

    @Override
    public MembershipModel createMembership(CreateMembershipModel membershipModel) {
        var user = usersRepository.findById(membershipModel.getUserId()).orElseThrow(() -> new MembershipNotFoundException("Membership not found"));
        var entity = MembershipMapper.mapCreateMembershipModelToMembership(membershipModel);
        entity.setUser(user);
        membershipRepository.save(entity);
        return MembershipMapper.mapMembershipToMembershipModel(entity);
    }

    @Override
    public MembershipModel updateMembership(MembershipModel membershipModel, Long membershipId) {
        var entity = MembershipMapper.mapMembershipModelToMembership(membershipModel);
        var newMembership = membershipRepository.findById(membershipId).orElseThrow(() -> new MembershipNotFoundException("Membership not found"));
//        if(entity.getTrainings() != null) {
//            newMembership.setTrainings(entity.getTrainings());
//        }
        if(entity.getUser() != null) {
            newMembership.setUser(entity.getUser());
        }
        if(entity.getStartDate() != null) {
            newMembership.setStartDate(entity.getStartDate());
        }
        if(entity.getExpireDate() != null) {
            newMembership.setExpireDate(entity.getExpireDate());
        }

        if(entity.getPrice() != null) {
            newMembership.setPrice(entity.getPrice());
        }

        if(entity.getActive() != null) {
            newMembership.setActive(entity.getActive());
        }

        return MembershipMapper.mapMembershipToMembershipModel(newMembership);
    }

    @Override
    public void deleteMembership(Long membershipId) {
        membershipRepository.deleteById(membershipId);
    }

    @Override
    public Map<String, Boolean> isActiveMembershipByUserId(Long userId) {
        var user = usersRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        var membership = membershipRepository.findByUser(user);
        var map = new HashMap<String, Boolean>();
        if(membership != null) {
            map.put("active", membership.getActive());
            return map;
        }
        map.put("active", false);
        return map;
    }
}
