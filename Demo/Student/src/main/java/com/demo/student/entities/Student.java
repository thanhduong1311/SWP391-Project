package com.demo.student.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class  Student {
    @Id //dat ID la khoa chinh
    @GeneratedValue(strategy = GenerationType.IDENTITY)// set primery key auto
    private int id;

    private String name;

    @Column(unique = true, nullable = false) // duy nhat de check
    private String code;

    private LocalDate dob;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @OneToOne (cascade = {CascadeType.PERSIST, CascadeType.REMOVE})// persist: luu cha thi luu luon con
    //Remove: xoa cha xoa con
    //All; tat ca
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

//    @Column(name = "student_description") // doi ten feild trng bang
//    private String description;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Student)) {
            return false;
        }

        return id == ((Student) obj).getId();
    }
}

