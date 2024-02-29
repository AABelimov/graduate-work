package ru.skypro.homework.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "pk")
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;

    @ManyToOne(cascade = CascadeType.ALL)
    private Ad ad;
    private String text;
    private Long createdAt;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
}
