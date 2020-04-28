package org.itachi.codestar.error;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by itachi on 2017/3/18.
 * User: itachi
 * Date: 2017/3/18
 * Time: 10:56
 * @author itachi
 */
public abstract class ServiceError {
    protected ServiceError() {}

    public  interface ErrorType {
        /**
         * 错误代码
         * @return 错误代码
         */
        int getCode();

        /**
         * 错误原因信息
         * @return 错误信息
         */
        String getReasonPhrase();
    }

    public enum Error implements ErrorType {
        /**
         * 非法请求
         */
        ILLEGAL_REQUEST(101, "非法请求"),
        /**
         * 不支持的数据类型
         */
        UN_SUPPORT_MEDIATYPE(102, "不支持的数据类型"),
        /**
         * 远程服务错误
         */
        REMOTE_SERVICE_ERROR(103, "远程服务错误"),
        /**
         * 参数错误
         */
        PARAMETER_INVALID(104, "参数错误"),
        /**
         * 非法的用户名
         */
        ACCOUNT_INVALID(105, "非法的用户名"),
        /**
         * 非法的密码
         */
        PASSWORD_INVALID(106, "非法的密码"),
        /**
         * 用户名或密码错误
         */
        USER_NOT_EXISTS(107, "用户名或密码错误"),
        /**
         * Id为空!
         */
        ID_EMPTY(108, "Id为空!"),
        /**
         * 数据不存在!
         */
        DATA_ONE(201, " 数据不存在!"),
        /**
         * 非法数据
         */
        DATA_TWO(202, " 存在非法数据!"),
        /**
         * 数据不存在!
         */
        ORDER_ID_EMPTY(203, "订单Id为空!"),
        /**
         * 订单不能被删除
         */
        ORDER_STATUS_UNMODIFY(204, "订单已排期，不能被删除!"),


        CUSTOMERNAME_NOT_EMPTY(301,"客户姓名不能为空"),
        POSITION_NOT_EMPTY(302,"职位不能为空"),
        PHONENUMBER_NOT_EMPTY(303,"电话号码不能为空"),
        PLEASE_CHOOSE_SEX(304,"请选性别"),
        COMPANYNAME_NOT_EMPTY(305,"公司名称不能为空"),
        COMPANYCODE_NOT_EMPTY(306,"公司代号不能为空"),
        PLEASE_SELECT_THE_AREA(307,"请选择区域"),
        PROVINCE_NOT_EMPTY(308,"请选择省"),
        CITY_NOT_EMPTY(309,"请选择市"),
        AREA_NOT_EMPTY(400,"请选择区/县"),

        CHILD_UNKNOWN(313,"删除的订单存在着设备，请先删除设备信息"),


        CHILD_PART(310,"添加零件已经存在"),
        CHILD_PLAN(311,"计划已经确定无法修改数量"),
        CHILD_ASSEMBLING(312,"存在装配未完成，请重新选择"),
        CHILD_PART_OUT(313,"该零件已经不存在，无法出库"),
        CHILD_PART_STATUS(314,"当前状态无法修改，请重新添加零件"),
        CHILD_DEVICE_STATUS(315,"当前计划已经确定，设备无法修改，请重新添加新的设备"),
        PART_STATUS(316,"零件库存不足，请先采购"),

        DEVICENAME_NOT_EMPTY(601,"设备名称不能为空"),
        DEVICENUMBER_NOT_EMPTY(602,"设备编号不能为空"),
        DEVICERULE_NOT_EMPTY(603,"设备规则不能为空"),
        DEVICESTANDARD_NOT_EMPTY(605,"设备标准不能为空"),

        CUSTOMER_NOT_EMPTY(701,"请选择客户"),
        EXPECTDATE_NOT_EMPTY(702,"请选择期望交货时间"),
        ORDERTIME_NOT_EMPTY(703,"请选择下单时间"),



        /**
         * 未知错误
         */
        UNKNOWN(999, "未知错误");




        private final int code;
        private final String reason;

        Error(final int errorCode, final String reasonPhrase) {
            code = errorCode;
            reason = reasonPhrase;
        }

        @Override
        public int getCode() {
            return code;
        }

        @Override
        public String getReasonPhrase() {
            return reason;
        }

        @Override
        public String toString() {
            return reason;
        }

        public static Error fromErrorCode(final int errorCode) {
            Optional<Error> optional = Stream.of(Error.values()).filter((Error error) -> error.code == errorCode).findFirst();
            return optional.orElse(UNKNOWN);
        }
    }
}
