package cn.bainuo.util;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;

/**
 * TraceId生成器
 *
 * @author DHC
 */
@Slf4j
public final class TraceIdGenerator {

    private static String[] IP_ARR;
    private static String IP_HEX;
    private static String PID_HEX;
    private static final SequenceScheme SEQUENCE_SCHEME = new SequenceScheme();
    private static final Integer IP_LENGTH = 4;
    private static String DOCKER_ID;


    static {
        IP_ARR = Strings.isNullOrEmpty(MachineInfo.getHostIp()) ? null : MachineInfo.getHostIp().split("\\.");
        StringBuffer sb = new StringBuffer();
        if (IP_ARR != null && IP_ARR.length == IP_LENGTH) {
            sb.append(Integer.toHexString(Integer.parseInt(IP_ARR[0])))
                    .append(Integer.toHexString(Integer.parseInt(IP_ARR[1])))
                    .append(Integer.toHexString(Integer.parseInt(IP_ARR[2])))
                    .append(Integer.toHexString(Integer.parseInt(IP_ARR[3])));
            IP_HEX = sb.toString();
        } else {
            throw new RuntimeException("Invalid IP format");
        }
        PID_HEX = Integer.toHexString(MachineInfo.getProcessNo());
        DOCKER_ID = DockerUtil.DCID();
        if (DOCKER_ID.length() > 12) {
            DOCKER_ID = DOCKER_ID.substring(0, 12);
        }
    }

    /**
     * traceId规则:
     * 12位dockerId
     * 前8位即产生TraceId的机器的IP的16进制，每两位可转换为10进制拼装后为具体IP
     * 后13位为产生TraceId的当前10进制毫秒数
     * 后4位为10进制顺序数，从1000开始到9999，到达9999重新递增
     * 后4位为16进制进程号，用于区分同Ip下不同应用
     *
     * @return traceId
     */
    public static String getTraceId() {
        long startTime = CachedMilliSecondClock.INS.now();
        StringBuffer sb = new StringBuffer(DOCKER_ID);
        sb.append(CachedMilliSecondClock.INS.now())
                .append(String.valueOf(SEQUENCE_SCHEME.nextSeq()))
                .append(PID_HEX);
        log.debug("generator traceId costs time:{}ms", CachedMilliSecondClock.INS.now() - startTime);
        return sb.toString();
    }

    private TraceIdGenerator() {
    }

    static class SequenceScheme {
        private int seq = 1000;
        private int maxSeq = 9999;

        public synchronized int nextSeq() {
            if (seq == maxSeq) {
                seq = 1000;
                return maxSeq;
            } else {
                return seq++;
            }
        }
    }
}
