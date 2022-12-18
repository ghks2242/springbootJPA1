package jpabook.jpashop.domain.item;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data
@DiscriminatorValue("M")
public class Movie extends Item{

    private String director;
    private String actor;
}
