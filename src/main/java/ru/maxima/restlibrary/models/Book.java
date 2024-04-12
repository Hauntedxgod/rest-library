package ru.maxima.restlibrary.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "book")
public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" )
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "year_Of_Production")
    private Integer yearOfProduction;

    @Column(name = "author")
    private String author;

    @Column(name = "annotation")
    private String annotation;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    @CreationTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "removed_at")
    private LocalDateTime removedAt;


    @Column(name = "created_Person")
    private String createdPerson;

    @Column(name = "update_Person")
    private String updatedPerson;

    @Column(name = "removed_Person")
    private String removedPerson;

    @ManyToOne
    private Person owner;

}
