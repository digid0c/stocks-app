package org.example.persistence.entity;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "share")
@Data
public class Share {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "company_name", nullable = false, length = 200)
    private String companyName;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "isin_code", nullable = false, length = 12)
    private String isinCode;

    @Column(name = "country_code", nullable = false, length = 3)
    private String countryCode;

    @Column(name = "activity_field", nullable = false, length = 200)
    private String activityField;
}
