package org.itachi.codestar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * TODO
 *
 * @author itachi
 * @since 2018/3/21 08:52
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderSchedule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private Long detailId;
    private Long deviceId;
    private String deliveryTime;
    private String status;
    @Transient
    private String orderTime;
    @Transient
    private String orderNumber;
    @Transient
    private String customerName;
    @Transient
    private String deviceNumber;
    @Transient
    private String deviceName;

}
