package cn.bainuo.util;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 服务器基础信息
 *
 * @author DHC
 */
@Slf4j
public final class MachineInfo {

    private static int PROCESS_NO = -1;
    private static String IP;
    private static String DCID = null;

    static {
        PROCESS_NO = getProcessNo();
    }

    public static int getProcessNo() {
        if (PROCESS_NO == -1) {
            String name = ManagementFactory.getRuntimeMXBean().getName();
            try {
                PROCESS_NO = Integer.parseInt(name.split("@")[0]);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("get process num error,cause:", e);
                PROCESS_NO = 0;
            }

        }
        return PROCESS_NO;
    }

    public static String getDockerId() {
        if (DCID == null) {
            String name = ManagementFactory.getRuntimeMXBean().getName();
            try {
                DCID = name.split("@")[1];
            } catch (Exception e) {
                e.printStackTrace();
                log.error("get docker id error,cause:", e);
                DCID = "";
            }

        }
        return DCID;
    }


    public static String getHostIp() {
        if (!Optional.fromNullable(IP).isPresent()) {
            String localip = null;// 本地IP，如果没有配置外网IP则返回它
            String netip = null;// 外网IP

            try {
                Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
                InetAddress ip = null;
                boolean finded = false;// 是否找到外网IP
                while (netInterfaces.hasMoreElements() && !finded) {
                    NetworkInterface ni = netInterfaces.nextElement();
                    Enumeration<InetAddress> address = ni.getInetAddresses();
                    while (address.hasMoreElements()) {
                        ip = address.nextElement();
                        if (!ip.isSiteLocalAddress()
                                && !ip.isLoopbackAddress()
                                && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                            netip = ip.getHostAddress();
                            finded = true;
                            break;
                        } else if (ip.isSiteLocalAddress()
                                && !ip.isLoopbackAddress()
                                && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                            localip = ip.getHostAddress();
                        }
                    }
                }
            } catch (SocketException e) {
                log.error("获取本机ip失败,cause:", e);
            }

            if (!Strings.isNullOrEmpty(netip)) {
                log.info("本机外网ip为{}", netip);
                IP = netip;
            } else {
                log.info("未找到本机外网ip,返回内网ip为{}", localip);
                IP = localip;
            }
        }
        return IP;
    }


    private MachineInfo() {
    }

}
