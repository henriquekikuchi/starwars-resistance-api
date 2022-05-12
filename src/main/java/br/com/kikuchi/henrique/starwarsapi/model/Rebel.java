package br.com.kikuchi.henrique.starwarsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rebel {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id", name = "location_id")
    private Location location;

    @OneToMany(mappedBy="rebel", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<RebelResource> resources;

    @OneToMany(mappedBy = "accused", cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<Report> reportedList;

    private boolean betrayer;
}
