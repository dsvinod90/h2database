package org.h2.value;

import org.h2.Ipv6;
import org.h2.api.ErrorCode;
import org.h2.engine.CastDataProvider;
import org.h2.engine.Mode;
import org.h2.message.DbException;
import org.h2.util.JdbcUtils;

import java.security.NoSuchAlgorithmException;

public class ValueIpv6 extends Value {
    protected Ipv6 ipv6Address;

    ValueIpv6(String ipv6Address) throws NoSuchAlgorithmException {
        this.ipv6Address = new Ipv6(ipv6Address);
    }

    public ValueIpv6(Ipv6 ipv6Address){
        this.ipv6Address = ipv6Address;
    }

    public static ValueIpv6 get(Ipv6 ipv6Object)  throws NoSuchAlgorithmException {
        ValueIpv6 valueIpv6Object = new ValueIpv6(ipv6Object);
        return valueIpv6Object;
    }

    @Override
    public String getString() {
        return ipv6Address.toString();
    }

    @Override
    public TypeInfo getType() {
        return TypeInfo.getTypeInfo(getValueType());
    }

    @Override
    public int getValueType() {
        return Value.IPV6;
    }

    @Override
    public int hashCode() {
        System.out.println("Hashcode: " + ipv6Address.hashCode());
        return ipv6Address.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        System.out.println(ipv6Address.toString() + "<-Match->" + ((ValueIpv6) other).getString());
        return other instanceof ValueIpv6
                && ipv6Address.equals(((ValueIpv6) other).ipv6Address);
    }

    @Override
    public  Value convertForAssignTo(TypeInfo targetType, CastDataProvider provider, Object column) {
        if (getType() == targetType) {
            return this;
        }
        switch (targetType.getValueType()) {
            case Value.VARCHAR: {
                return ValueVarchar.get(ipv6Address.toString(), provider);
            }
        }
        throw DbException.get(
                ErrorCode.DATA_CONVERSION_ERROR_1, getString() + "*********ERROR******");
    }
//    @Override
//    public Value convertTo(int targetType, CastDataProvider provider) {
//        if (getValueType() == targetType) {
//            return this;
//        }
//        switch (targetType) {
//            case Value.VARCHAR: {
//                return ValueVarchar.get(ipv4Address.toString(), provider);
//            }
//        }
//        throw DbException.get(
//                ErrorCode.DATA_CONVERSION_ERROR_1, getString() + "*********ERROR******");
//    }

    @Override
    public int compareTypeSafe(Value v, CompareMode mode, CastDataProvider provider) {
        return 0;
    }

    @Override
    public StringBuilder getSQL(StringBuilder builder, int sqlFlags) {
        return null;
    }
}
