package br.com.kikuchi.henrique.starwarsapi.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = Rebel.class)
    private Rebel accuser;

    @ManyToOne(targetEntity = Rebel.class)
    private Rebel accused;
}
