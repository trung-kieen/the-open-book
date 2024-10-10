// package com.example.the_open_book.common;

// import java.io.Serializable;
// import java.time.LocalDateTime;

// import org.antlr.v4.runtime.misc.Interval;
// import org.springframework.data.annotation.CreatedBy;
// import org.springframework.data.annotation.CreatedDate;
// import org.springframework.data.annotation.LastModifiedDate;
// import org.springframework.data.jpa.domain.support.AuditingEntityListener;

// import jakarta.persistence.Column;
// import jakarta.persistence.EntityListeners;
// import jakarta.persistence.MappedSuperclass;
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;
// import lombok.experimental.SuperBuilder;

// /**
//  * BaseEntity
//  */
// @Getter
// @Setter
// @SuperBuilder
// @AllArgsConstructor
// @NoArgsConstructor
// @MappedSuperclass
// @EntityListeners(AuditingEntityListener.class)
// public class BaseEntity implements Serializable{


//   @CreatedDate
//   @Column(name = "create_at", nullable =  false , updatable =  false )
//   private LocalDateTime createAt;

//   @LastModifiedDate
//   @Column(name = "last_modified_date", nullable =  false , insertable =  false )
//   private LocalDateTime lastModifiedDate;

//   @CreatedBy
//   private Interval createdBy;

//   @Column(name =  "last_modified_date")
//   private Integer lastModifiedBy;
// }
