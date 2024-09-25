package cm.ex.delivery.entity.user;

import cm.ex.delivery.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @NotBlank(message="please enter order data")
    @Column(name = "order_number")
    private String orderNumber;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @NotBlank(message="please enter user order data")
    private User customer;

    @NotBlank(message="please enter order data")
    @Column(name = "total_amount")
    private int totalAmount;

    @Column(name = "order_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime orderDate;

    @NotBlank(message="please enter order data")
    @Column(name = "shipping_method")
    private String shippingMethod; //normal or express

    @NotBlank(message="please enter order data")
    @Column(name = "shipping_cost")
    private int shippingCost;

    @NotBlank(message="please enter order data")
    @Column(name = "final_amount")
    private int finalAmount; //when discount or couple are added and shipping fee

    @NotBlank(message="please enter order data")
    @Column(name = "order_status", columnDefinition = "VARCHAR(255) DEFAULT 'processing'")
    private String orderStatus; //processing,

    @NotBlank(message="please enter order data")
    @Column(name = "payment_status", columnDefinition = "VARCHAR(255) DEFAULT 'pending'")
    private String paymentStatus; // paid, pending, failed, cash on delivery

    @NotBlank(message="please enter order data")
    @Column(name = "shipment_status", columnDefinition = "VARCHAR(255) DEFAULT 'not shipped'")
    private String shipmentStatus; //not shipped, shipping, shipped, out of delivery, failed delivery

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    @OneToOne(optional = true)
    @JoinColumn(name = "carrier_id", referencedColumnName = "id")
    private User carrier;

}
