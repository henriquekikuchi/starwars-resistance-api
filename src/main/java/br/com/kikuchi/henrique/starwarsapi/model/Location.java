package br.com.kikuchi.henrique.starwarsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Float latitude;

    private Float longitude;

    private String name;

    @Column(columnDefinition = "boolean default false")
    private Boolean updatedByAdmin;

    @OneToOne(mappedBy = "location")
    @JsonIgnore
    private Rebel rebel;

}