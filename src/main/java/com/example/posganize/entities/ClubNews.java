package com.example.posganize.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "club_news")
public class ClubNews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long news_id;
    @Column(name = "name")
    @NotBlank(message = "Club news name is mandatory")
    private String title;
    @Column(name = "description")
    @NotBlank(message = "Club news description is mandatory")
    private String description;
    @NotBlank(message = "Club news image is mandatory")
    private String image;
    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private Users user;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
