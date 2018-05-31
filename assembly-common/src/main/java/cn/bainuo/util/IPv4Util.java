package cn.bainuo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.net.InetAddress;

@Slf4j
public class IPv4Util {

    private final static int INADDRSZ = 4;

    /**
     * IP转换为byte数组 ipToBytesByInet:(IP转换为byte数组). <br/>
     *
     * @author wanglz
     * @param ipAddr
     * @return
     * @since JDK 1.6
     */
    public static byte[] ipToBytesByInet(String ipAddr) {
        try {
            return InetAddress.getByName(ipAddr).getAddress();
        } catch (Exception e) {
            throw new IllegalArgumentException(ipAddr + " is invalid IP");
        }
    }

    public static byte[] ipToBytesByReg(String ipAddr) {
        byte[] ret = new byte[4];
        try {
            String[] ipArr = ipAddr.split("\\.");
            ret[0] = (byte) (Integer.parseInt(ipArr[0]) & 0xFF);
            ret[1] = (byte) (Integer.parseInt(ipArr[1]) & 0xFF);
            ret[2] = (byte) (Integer.parseInt(ipArr[2]) & 0xFF);
            ret[3] = (byte) (Integer.parseInt(ipArr[3]) & 0xFF);
            return ret;
        } catch (Exception e) {
            throw new IllegalArgumentException(ipAddr + " is invalid IP");
        }
    }

    public static String bytesToIp(byte[] bytes) {
        return new StringBuffer().append(bytes[0] & 0xFF).append('.').append(bytes[1] & 0xFF).append('.')
                .append(bytes[2] & 0xFF).append('.').append(bytes[3] & 0xFF).toString();
    }

    public static int bytesToInt(byte[] bytes) {
        int addr = bytes[3] & 0xFF;
        addr |= ((bytes[2] << 8) & 0xFF00);
        addr |= ((bytes[1] << 16) & 0xFF0000);
        addr |= ((bytes[0] << 24) & 0xFF000000);
        return addr;
    }

    public static int ipToInt(String ipAddr) {
        try {
            return bytesToInt(ipToBytesByInet(ipAddr));
        } catch (Exception e) {
            throw new IllegalArgumentException(ipAddr + " is invalid IP");
        }
    }

    public static byte[] intToBytes(int ipInt) {
        byte[] ipAddr = new byte[INADDRSZ];
        ipAddr[0] = (byte) ((ipInt >>> 24) & 0xFF);
        ipAddr[1] = (byte) ((ipInt >>> 16) & 0xFF);
        ipAddr[2] = (byte) ((ipInt >>> 8) & 0xFF);
        ipAddr[3] = (byte) (ipInt & 0xFF);
        return ipAddr;
    }

    public static String intToIp(int ipInt) {
        return new StringBuilder().append(((ipInt >> 24) & 0xff)).append('.').append((ipInt >> 16) & 0xff).append('.')
                .append((ipInt >> 8) & 0xff).append('.').append((ipInt & 0xff)).toString();
    }

    public static int[] getIPIntScope(String ipAndMask) {
        String[] ipArr = ipAndMask.split("/");
        if (ipArr.length != 2) {
            throw new IllegalArgumentException("invalid ipAndMask with: " + ipAndMask);
        }
        int netMask = Integer.valueOf(ipArr[1].trim());
        if (netMask < 0 || netMask > 31) {
            throw new IllegalArgumentException("invalid ipAndMask with: " + ipAndMask);
        }
        int ipInt = IPv4Util.ipToInt(ipArr[0]);
        int netIP = ipInt & (0xFFFFFFFF << (32 - netMask));
        int hostScope = (0xFFFFFFFF >>> netMask);
        return new int[] { netIP, netIP + hostScope };
    }

    public static String[] getIPAddrScope(String ipAndMask) {
        int[] ipIntArr = IPv4Util.getIPIntScope(ipAndMask);
        return new String[] { IPv4Util.intToIp(ipIntArr[0]), IPv4Util.intToIp(ipIntArr[0]) };
    }

    public static int[] getIPIntScope(String ipAddr, String mask) {
        int ipInt;
        int netMaskInt = 0, ipcount = 0;
        try {
            ipInt = IPv4Util.ipToInt(ipAddr);
            if (null == mask || "".equals(mask)) {
                return new int[] { ipInt, ipInt };
            }
            netMaskInt = IPv4Util.ipToInt(mask);
            ipcount = IPv4Util.ipToInt("255.255.255.255") - netMaskInt;
            int netIP = ipInt & netMaskInt;
            int hostScope = netIP + ipcount;
            return new int[] { netIP, hostScope };
        } catch (Exception e) {
            throw new IllegalArgumentException("invalid ip scope express ip:" + ipAddr + " mask:" + mask);
        }
    }

    public static String[] getIPStrScope(String ipAddr, String mask) {
        int[] ipIntArr = IPv4Util.getIPIntScope(ipAddr, mask);
        return new String[] { IPv4Util.intToIp(ipIntArr[0]), IPv4Util.intToIp(ipIntArr[0]) };
    }

    /**
     * 判断IP是否在IP段内(#46267) isInRange:(判断IP是否在IP段内). <br/>
     * 1.ip段 '头IP-末IP'，如 192.168.120.1-192.168.120.254
     *
     * @author wanglz
     * @param realIP
     * @param ipRange
     * @return
     * @since JDK 1.6
     */
    public static boolean isInRange(String realIP, String ipRange) {
        if (StringUtils.isEmpty(ipRange)) {
            log.info("IP段校验 IP:{} => [{}] ip is null is match.", new Object[] { realIP, ipRange });
            return true;
        }
        String[] ipArr = ipRange.split("-");
        if (ipArr.length == 2) {
            int ipInt1 = IPv4Util.ipToInt(ipArr[0]);
            int ipInt2 = IPv4Util.ipToInt(ipArr[1]);
            int ipInt = IPv4Util.ipToInt(realIP);

            if (ipInt >= ipInt1 && ipInt <= ipInt2) {
                log.info("IP段校验 IP:{} => {} [{},{}] is match", new Object[] { realIP, ipInt, ipInt1, ipInt2 });
                return true;
            }
            log.info("IP段校验 IP:{} => {} [{},{}] is not match", new Object[] { realIP, ipInt, ipInt1, ipInt2 });
            return false;
        }
        log.info("IP段校验 IP:{} => [{}] ip range array length != 2 is match.", new Object[] { realIP, ipRange });
        return true;
    }

}
