package org.h2.value;

import org.h2.Ipv4;
import org.h2.api.ErrorCode;
import org.h2.engine.CastDataProvider;
import org.h2.engine.Mode;
import org.h2.message.DbException;
import org.h2.util.JdbcUtils;

import java.security.NoSuchAlgorithmException;

public class ValueIpv4 extends Value {
    protected Ipv4 ipv4Address;

    ValueIpv4(String ipv4Address) throws NoSuchAlgorithmException {
        this.ipv4Address = new Ipv4(ipv4Address);
    }

    public ValueIpv4(Ipv4 ipv4Address){
        this.ipv4Address = ipv4Address;
    }

    public static ValueIpv4 get(Ipv4 ipv4Object)  throws NoSuchAlgorithmException {
        ValueIpv4 valueIpv4Object = new ValueIpv4(ipv4Object);
        return valueIpv4Object;
    }

    @Override
    public String getString() {
        return ipv4Address.toString();
    }

    @Override
    public TypeInfo getType() {
        return TypeInfo.getTypeInfo(getValueType());
    }

    @Override
    public int getValueType() {
        return Value.IPV4;
    }

    @Override
    public int hashCode() {
        System.out.println("Hashcode: " + ipv4Address.hashCode());
        return ipv4Address.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        System.out.println(ipv4Address.toString() + "<-Match->" + ((ValueIpv4) other).getString());
        return other instanceof ValueIpv4
                && ipv4Address.equals(((ValueIpv4) other).ipv4Address);
    }

    @Override
    public  Value convertForAssignTo(TypeInfo targetType, CastDataProvider provider, Object column) {
        if (getType() == targetType) {
            return this;
        }
        switch (targetType.getValueType()) {
            case Value.VARCHAR: {
                return ValueVarchar.get(ipv4Address.toString(), provider);
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
