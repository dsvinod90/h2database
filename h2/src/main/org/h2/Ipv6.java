package org.h2;

import org.h2.api.ErrorCode;
import org.h2.message.DbException;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ipv6 implements Serializable {

    private static final long serialVersionUID = 1L;
    private String ipAddress;

    public Ipv6(String ipAddress) throws NoSuchAlgorithmException {
        verifyAndInitializeIpv6(ipAddress);
    }

    public void verifyAndInitializeIpv6(String ipAddress) throws NoSuchAlgorithmException{
        String ipMatchRegex = "^(?:(?:(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4})|(?::[0-9a-fA-F]{1,4}){1,8})(%.+)?$";
        Pattern ipPattern = Pattern.compile(ipMatchRegex, Pattern.CASE_INSENSITIVE);
        Matcher ipMatcher = ipPattern.matcher(ipAddress);
        if (ipMatcher.matches()){
            this.ipAddress = ipAddress.toString();
        } else {
            System.out.println("Exception thrown!!");
            throw DbException.get(ErrorCode.IPV6_ERROR_CODE);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ipv6 other = (Ipv6) obj;
        if (ipAddress == null) {
            if (other.ipAddress != null)
                return false;
        } else if (!ipAddress.equals(other.ipAddress))
            return false;
        return true;
    }

    @Override
    public String toString(){
        return ipAddress;
    }

    @Override
    public int hashCode() {
        // randomly generate hash code.
        final int prime = 91;
        return 4 * prime *  + ((ipAddress == null) ? 0 : ipAddress.hashCode());
    }
}
