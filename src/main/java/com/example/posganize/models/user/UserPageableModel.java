package com.example.posganize.models.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPageableModel {
    private List<UsersModel> users;
    private int pageNumber;
    private int pageSize;
    private Long numberOfUsers;
    private int totalPages;
    private boolean isLast;
    private boolean isFirst;
    private boolean hasPrevious;
    private boolean hasNext;
}
