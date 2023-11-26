/*
 * Copyright 2004-2023 H2 Group. Multiple-Licensed under the MPL 2.0,
 * and the EPL 1.0 (https://h2database.com/html/license.html).
 * Initial Developer: H2 Group
 */
package org.h2.expression.aggregate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import org.h2.api.IntervalQualifier;
import org.h2.engine.SessionLocal;
import org.h2.util.IntervalUtils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueDecfloat;
import org.h2.value.ValueDouble;
import org.h2.value.ValueInterval;
import org.h2.value.ValueNull;
import org.h2.value.ValueNumeric;

/**
 * Data stored while calculating an AVG aggregate.
 */
final class AggregateDataGeoMean extends AggregateData {

    private final TypeInfo dataType;
    private long count;
    private double doubleValue = 1;
    private BigDecimal decimalValue = BigDecimal.valueOf(1);
    private BigInteger integerValue = BigInteger.valueOf(1);

    /**
     * @param dataType
     *            the data type of the computed result
     */
    AggregateDataGeoMean(TypeInfo dataType) {
        this.dataType = dataType;
    }

    @Override
    void add(SessionLocal session, Value v) {
        System.out.println(v + "ADD GEOMEAN!!!!!");
        if (v == ValueNull.INSTANCE) {
            return;
        }
        count++;
//        System.out.println(dataType.getValueType());
        switch (dataType.getValueType()) {
            case Value.DOUBLE:
                doubleValue *= v.getDouble();
                System.out.println(doubleValue);
                break;
            case Value.NUMERIC:
            case Value.DECFLOAT: {
                BigDecimal bd = v.getBigDecimal();
                decimalValue = decimalValue == null ? bd : decimalValue.multiply(bd);
                break;
            }
            default: {
                BigInteger bi = IntervalUtils.intervalToAbsolute((ValueInterval) v);
                integerValue = integerValue == null ? bi : integerValue.multiply(bi);
            }
        }
    }

    @Override
    Value getValue(SessionLocal session) {
        System.out.println("GEOMEAN!!!!!");
        if (count == 0) {
            return ValueNull.INSTANCE;
        }
        Value v;
        int valueType = dataType.getValueType();
        switch (valueType) {
            case Value.DOUBLE:
                double rootValue = Math.pow(doubleValue, 1.0/count);
                v = ValueDouble.get(rootValue);
                break;
            case Value.NUMERIC:
            case Value.DECFLOAT:
                BigDecimal rootNumericValue = BigDecimal.valueOf(Math.pow(decimalValue.doubleValue(), 1.0/count));
                v = ValueNumeric.get(rootNumericValue);
                break;
            default:
                v = IntervalUtils.intervalFromAbsolute(IntervalQualifier.valueOf(valueType - Value.INTERVAL_YEAR),
                        BigInteger.valueOf((long) Math.pow(integerValue.doubleValue(), 1.0/count)));
        }
        return v.castTo(dataType, session);
    }

}
