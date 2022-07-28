package uz.pdp.news_app.entity.template;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import uz.pdp.news_app.entity.User;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@MappedSuperclass
public class AbsEntity {
    //uuid
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    //security ishlatiladigan
    //kim qo'shdi user uuid

    @CreatedBy
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "created_by")
    private User createdBy;

//    //kim o'zgartirdi
//    @LastModifiedBy
//    @Column(nullable = false)
//    private UUID updatedBy;

    //qachon qo'shgan
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdAt;

    //qachon o'zgardi
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;
}
