package com.seven.Blog.API.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private Long likes;

    @Column
    private Long views;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private Set<Comment> commentSet;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "title = " + title + ", " +
                "description = " + description + ", " +
                "likes = " + likes + ", " +
                "views = " + views + ")";
    }
}