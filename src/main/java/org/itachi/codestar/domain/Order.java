package org.itachi.codestar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * TODO
 *
 * @author itachi
 * @since 2018/3/20 20:22
 */
@Entity
@Table(name = "order_t")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private String number;
    private String time;
    private String status;
    private String storageTime;
    private Long createTime;
    private String createDate;
    private Integer date;
    private int deleted;
    @Transient
    private String customerName;
    @Transient
    private String companyCode;
    @Transient
    private String companyName;
    @Transient
    private String deliveryTime;
    @Transient
    private List<OrderDetail> devices;

}
