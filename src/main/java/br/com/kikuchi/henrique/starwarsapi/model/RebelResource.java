package br.com.kikuchi.henrique.starwarsapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RebelResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ResourcesEnum resource;

    @ManyToOne(targetEntity = Rebel.class)
    @JsonBackReference
    private Rebel rebel;

    private Integer quantity;
}
