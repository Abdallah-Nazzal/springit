package com.qawas.springit.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Vote extends Auditable {


    @Id
    @GeneratedValue
    private long id;

    @NonNull
    private short direction;

    @NonNull
    @ManyToOne
    private Link link;

}
