package org.itachi.codestar.sql;

/**
 * TODO
 *
 * @author itachi
 * @since 2018/3/19 15:05
 */
public interface SqlConstants {
    String DEVICE_SQL = "select a.*,t.*,c.company_name,c.company_code,b.order_time,b.delivery_time from order_device a inner join order_manage b inner join customer c inner join device_t t on a.device_id = b.id on c.id =b.customer_id on t.id =a.device_id";

    String DEVICE_MANAGE_SQL = "select a.id as order_id,b.* from order_device a inner join order_manage b on a.device_id=b.id ";

    String LIMIT_SQL = " limit :offset, :rows";
}
