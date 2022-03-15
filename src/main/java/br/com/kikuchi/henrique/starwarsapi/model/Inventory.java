package br.com.kikuchi.henrique.starwarsapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Getter
    private Integer id;

    @Getter
    private Boolean blocked;

//    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
//    @Getter
//    private List<RebelResource> rebelResourceList;

//    @OneToOne(mappedBy = "inventory")
//    private Rebel rebel;

    @PrePersist
    void setInitialValues(){
        setBlocked(false);
    }
}
