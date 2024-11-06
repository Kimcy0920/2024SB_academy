package org.zerock.guestbook.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
//이 애너테이션은 이 클래스가 JPA 엔티티의 기본 클래스임을 나타냅니다.
//자체적으로 데이터베이스 테이블에 매핑되지 않지만 하위 클래스에 필드와 매핑을 제공할 수 있습니다.
@EntityListeners(value = { AuditingEntityListener.class })
//이 설정은 엔티티에 대한 감사를 활성화하여 regDate 및 modDate와 같은 필드가 자동으로 채워지도록 합니다.
@Getter
abstract class BaseEntity {

    @CreatedDate
    //이 애너테이션은 엔티티가 처음으로 영속화될 때 regDate 필드를 자동으로 설정합니다.
    // updatable = false 속성은 이 필드가 설정된 후에는 업데이트되지 않도록 합니다.
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    //이 애너테이션은 엔티티가 수정될 때마다 modDate 필드를 자동으로 업데이트합니다.
    @Column(name ="moddate" )
    private LocalDateTime modDate;

}
